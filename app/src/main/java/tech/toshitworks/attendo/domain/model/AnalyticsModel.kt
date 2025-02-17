package tech.toshitworks.attendo.domain.model

data class AnalyticsModel(
    val subject: SubjectModel?,
    val lecturesConducted: Int,
    val lecturesPresent: Int,
    val totalHours: Int,
    val analyticsByDay: List<AnalyticsByDay>,
    val analysisByWeek: List<AnalyticsByWeek>,
    val analysisByMonth: List<AnalyticsByMonth>,
    val streak: Pair<Int,Int>?,
    val periodAnalysis: List<PeriodAnalysis>? = null,
    val eligibilityOfMidterm: EligibilityData?,
    val eligibilityOfEndSem: EligibilityData?
)

data class AnalyticsByDay(
    val day: DayModel,
    val lecturesConducted: Int,
    val lecturesPresent: Int,
)
data class AnalyticsByWeek(
    val yearWeek: String,
    val lecturesConducted: Int,
    val lecturesPresent: Int,
)
data class AnalyticsByMonth(
    val yearMonth: String,
    val lecturesConducted: Int,
    val lecturesPresent: Int,
)
