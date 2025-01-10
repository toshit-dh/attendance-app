package tech.toshitworks.attendo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.toshitworks.attendo.data.entity.EventEntity
import tech.toshitworks.attendo.data.local.EventDao
import tech.toshitworks.attendo.domain.model.EventModel
import tech.toshitworks.attendo.domain.repository.EventRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import java.util.UUID
import javax.inject.Inject

class EventRepoImpl @Inject constructor(
    private val eventDao: EventDao,
    private val subjectRepository: SubjectRepository
): EventRepository {
    override fun getEvents(): Flow<List<EventModel>> {
        return eventDao.getEvents().map { ee ->
            ee.map {
                val subject = subjectRepository.getSubjectById(it.subjectId)!!
                EventModel(
                    id = it.id,
                    date = it.date,
                    content = it.content,
                    subjectModel = subject,
                    notificationWorkId = if (it.notificationWorkId.isNullOrEmpty() || it.notificationWorkId == "null") {
                        null
                    } else {
                        UUID.fromString(it.notificationWorkId)
                    }
                )
            }
        }
    }


    override suspend fun addEvent(event: EventModel) {
        eventDao.addEvent(
            EventEntity(
                id = event.id,
                subjectId = event.subjectModel.id,
                date = event.date,
                content = event.content,
                notificationWorkId = event.notificationWorkId.toString()
            )
        )
    }

    override suspend fun deleteEvent(event: EventModel) {
        eventDao.deleteEvent(
            EventEntity(
                id = event.id,
                subjectId = event.subjectModel.id,
                date = event.date,
                content = event.content
            )
        )

    }
}