package tech.toshitworks.attendancechahiye.domain.repository

import tech.toshitworks.attendancechahiye.domain.model.AnalyticsModel

interface AnalyticsRepository {


    suspend fun getAnalysis(): List<AnalyticsModel>


}