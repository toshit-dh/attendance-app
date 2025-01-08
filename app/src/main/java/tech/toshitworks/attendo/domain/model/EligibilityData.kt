package tech.toshitworks.attendo.domain.model

data class EligibilityData(
    val isEligibleForNow: Boolean,
    val isEligibleInFuture: Boolean,
    val bunkLectures: Int,
    val moreLectures: Int,
    val isDone: Boolean
)
