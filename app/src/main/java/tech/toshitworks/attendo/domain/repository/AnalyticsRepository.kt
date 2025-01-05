package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.domain.model.AnalyticsModel

interface AnalyticsRepository {


    suspend fun getAnalysis(startDate: String,midTermDate: String?,endSemDate: String?): List<AnalyticsModel>


}