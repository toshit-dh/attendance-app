package tech.toshitworks.attendo.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.domain.model.EventModel

interface EventRepository {

    fun getEvents(): Flow<List<EventModel>>

    suspend fun addEvent(event: EventModel)

    suspend fun deleteEvent(event: EventModel)


}