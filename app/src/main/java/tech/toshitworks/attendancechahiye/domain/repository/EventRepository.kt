package tech.toshitworks.attendancechahiye.domain.repository

import tech.toshitworks.attendancechahiye.domain.model.EventModel

interface EventRepository {

    suspend fun getEvents(): List<EventModel>

}