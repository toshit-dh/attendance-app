package tech.toshitworks.attendancechahiye.data.repository

import tech.toshitworks.attendancechahiye.data.local.DayDao
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.mapper.toEntity
import tech.toshitworks.attendancechahiye.mapper.toModel
import javax.inject.Inject

class DayRepositoryImpl @Inject constructor(
    private val dayDao: DayDao
): DayRepository{
    override suspend fun insertDays(days: List<DayModel>) {
        dayDao.insertDays(days.map {
            it.toEntity()
        })
    }
    override suspend fun getDays(): List<DayModel> {
        return dayDao.getDays().map {
            it.toModel()
        }
    }
}