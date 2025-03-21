package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.domain.model.PeriodModel

interface PeriodRepository {

    suspend fun insertPeriod(period: PeriodModel)

    suspend fun getPeriodById(periodId: Long): PeriodModel

    suspend fun getAllPeriods(): List<PeriodModel>

    suspend fun insertPeriods(periods: List<PeriodModel>)

    suspend fun updatePeriod(period: PeriodModel)

    suspend fun getPeriods(): List<PeriodModel>

    suspend fun getPeriodByStartTime(startTime: String): PeriodModel
}