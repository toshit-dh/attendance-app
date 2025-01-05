package tech.toshitworks.attendo.data.repository

import tech.toshitworks.attendo.data.local.DayDao
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.mapper.toEntity
import tech.toshitworks.attendo.mapper.toModel
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

    override suspend fun getDayByName(dayName: String): DayModel {
        return dayDao.getDayByName(name = dayName)?.toModel() ?: DayModel(
            0,""
        )
    }

    override suspend fun getDayById(dayId: Long): DayModel {
        return dayDao.getDayById(dayId).toModel()
    }
}