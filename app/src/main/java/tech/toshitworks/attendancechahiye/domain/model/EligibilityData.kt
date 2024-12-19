package tech.toshitworks.attendancechahiye.domain.model

data class EligibilityData(
    val isEligibleForNow: Boolean,
    val isEligibleInFuture: Boolean,
    val bunkLectures: Int,
    val moreLectures: Int
)
