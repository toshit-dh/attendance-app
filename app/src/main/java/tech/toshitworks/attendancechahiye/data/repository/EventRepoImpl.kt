package tech.toshitworks.attendancechahiye.data.repository

import tech.toshitworks.attendancechahiye.data.local.EventDao
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.domain.repository.EventRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import javax.inject.Inject

class EventRepoImpl @Inject constructor(
    private val eventDao: EventDao,
    private val subjectRepository: SubjectRepository
): EventRepository {
    override suspend fun getEvents(): List<EventModel> {
        return eventDao.getEvents().map {
            val subject = subjectRepository.getSubjectById(it.subjectId)!!
            EventModel(
                id = it.id,
                date = it.date,
                content = it.content,
                subjectModel = subject,
            )
        }
    }
}