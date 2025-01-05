package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.domain.model.DayModel

interface DayRepository {

    suspend fun insertDays(days: List<DayModel>)

    suspend fun getDays(): List<DayModel>

    suspend fun getDayByName(dayName: String): DayModel

    suspend fun getDayById(dayId: Long): DayModel

}