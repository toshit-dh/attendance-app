package tech.toshitworks.attendancechahiye.data.repository

import tech.toshitworks.attendancechahiye.data.local.PeriodDao
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.mapper.toEntity
import tech.toshitworks.attendancechahiye.mapper.toModel
import javax.inject.Inject

class PeriodRepositoryImpl @Inject constructor(
    private val periodDao: PeriodDao
): PeriodRepository {
    override suspend fun insertPeriod(period: PeriodModel) {
        periodDao.insertPeriod(period.toEntity())
    }

    override suspend fun getPeriodById(periodId: Long): PeriodModel {
        return periodDao.getPeriodById(periodId).toModel()
    }

    override suspend fun getAllPeriods(): List<PeriodModel> {
        return periodDao.getAllPeriods().map {
            it.toModel()
        }
    }

    override suspend fun insertPeriods(periods: List<PeriodModel>) {
        periodDao.insertPeriods(periods.map {
            it.toEntity()
        })
    }

    override suspend fun updatePeriod(period: PeriodModel) {
        periodDao.updatePeriod(period.toEntity())
    }

    override suspend fun getPeriods(): List<PeriodModel> {
        return periodDao.getAllPeriods().map {
            it.toModel()
        }
    }

    override suspend fun getPeriodByStartTime(startTime: String): PeriodModel {
        return periodDao.getPeriodByStartTime(startTime).toModel()
    }
}