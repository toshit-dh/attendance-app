package tech.toshitworks.attendo.utils

import tech.toshitworks.attendo.domain.model.AnalyticsByWeek
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.EligibilityData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun countDaysBetweenDates(startDate: String, endDate: String): Map<String, Int> {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = LocalDate.parse(startDate, dateFormatter)
    val end = LocalDate.parse(endDate, dateFormatter)
    val dayCount = mutableMapOf<String, Int>()
    var currentDate = start
    while (!currentDate.isAfter(end)) {
        val dayOfWeek = currentDate.dayOfWeek.toString().lowercase()
        dayCount[dayOfWeek] = dayCount.getOrDefault(dayOfWeek, 0) + 1
        currentDate = currentDate.plusDays(1)
    }
    return dayCount
}

fun calculateEligibility(
    lecturesConducted: Int,
    lecturesPresent: Int,
    totalCountMidTerm: Int,
    totalCountEndSem: Int,
): Array<EligibilityData> {
    val ifMidTerm = if (totalCountMidTerm > 0) {
        val totalLecturesTillMidTerm = lecturesConducted + totalCountMidTerm
        val percentageAttendedMidTerm =
            (lecturesPresent.toFloat() / lecturesConducted.toFloat()) * 100
        val midTermEligible = percentageAttendedMidTerm >= 50
        val midTermResult = if (midTermEligible) {
            val remainingLecturesMidTerm =
                (totalLecturesTillMidTerm * 0.50).toInt() - lecturesPresent
            val bunkLectures = totalCountMidTerm - remainingLecturesMidTerm
            EligibilityData(
                isEligibleForNow = true,
                isEligibleInFuture = true,
                bunkLectures = bunkLectures,
                moreLectures = remainingLecturesMidTerm,
                isDone = false
            )
        } else {
            val remainingLectures = totalLecturesTillMidTerm - lecturesConducted
            val remainingLecturesMidTerm =
                (totalLecturesTillMidTerm * 0.50).toInt() - lecturesPresent
            val bunkLectures = remainingLectures - remainingLecturesMidTerm
            if (remainingLecturesMidTerm >= remainingLectures) {
                EligibilityData(
                    isEligibleForNow = false,
                    isEligibleInFuture = false,
                    bunkLectures = -1,
                    moreLectures = -1,
                    isDone = false
                )
            } else {
                EligibilityData(
                    isEligibleForNow = false,
                    isEligibleInFuture = true,
                    bunkLectures = bunkLectures,
                    moreLectures = remainingLecturesMidTerm,
                    isDone = false
                )
            }
        }
        midTermResult
    } else {
        EligibilityData(
            isEligibleForNow = false,
            isEligibleInFuture = false,
            bunkLectures = -1,
            moreLectures = -1,
            isDone = true
        )
    }
    val ifEndSem = if (totalCountEndSem > 0) {
        val percentageAttendedEndSem =
            (lecturesPresent.toFloat() / lecturesConducted.toFloat()) * 100
        val endSemEligible = percentageAttendedEndSem >= 75
        val totalLecturesTillEndSem = lecturesConducted + totalCountEndSem
        val endSemResult = if (endSemEligible) {
            val remainingLecturesEndSem = (totalLecturesTillEndSem * 0.75).toInt() - lecturesPresent
            val bunkLectures = totalCountEndSem - remainingLecturesEndSem
            EligibilityData(
                isEligibleForNow = true,
                isEligibleInFuture = true,
                bunkLectures = bunkLectures,
                moreLectures = remainingLecturesEndSem,
                isDone = false
            )
        } else {
            val remainingLectures = totalLecturesTillEndSem - lecturesConducted
            val remainingLecturesEndSem =
                (totalLecturesTillEndSem * 0.75).toInt() - lecturesPresent
            val bunkLectures = remainingLectures - remainingLecturesEndSem
            if (remainingLecturesEndSem >= remainingLectures) {
                EligibilityData(
                    isEligibleForNow = false,
                    isEligibleInFuture = false,
                    bunkLectures = -1,
                    moreLectures = -1,
                    isDone = false
                )
            } else EligibilityData(
                isEligibleForNow = false,
                isEligibleInFuture = true,
                bunkLectures = bunkLectures,
                moreLectures = remainingLecturesEndSem,
                isDone = false
            )
        }
        endSemResult
    } else {
        EligibilityData(
            isEligibleForNow = false,
            isEligibleInFuture = false,
            bunkLectures = -1,
            moreLectures = -1,
            isDone = true
        )
    }
    return arrayOf(ifMidTerm, ifEndSem)
}

fun calculateStreak(attendanceList: List<AttendanceModel>): Pair<Int, Int> {
    var current = 0
    var longest = 0
    for (attendanceModel in attendanceList) {
        if (attendanceModel.isPresent) {
            current++
            longest = maxOf(longest, current)
        } else {
            current = 0
        }
    }
    return Pair(current, longest)
}

fun combineWeeks(analyticsList: List<AnalyticsByWeek>): List<AnalyticsByWeek> {
    val result = mutableListOf<AnalyticsByWeek>()
    var previousYear = ""
    var previousIsoWeek = 0
    for (analytics in analyticsList) {
        val currentYear = analytics.yearWeek.substring(0, 4)
        val currentWeek = analytics.yearWeek.substring(5).toInt()
        val isoWeek = if (previousYear != currentYear) {
            adjustToIsoWeek(currentYear, currentWeek)
        } else {
            previousIsoWeek + 1
        }
        val adjustedYearWeek = "$currentYear-${isoWeek.toString().padStart(2, '0')}"
        result.add(
            analytics.copy(
                yearWeek = adjustedYearWeek
            )
        )
        previousYear = currentYear
        previousIsoWeek = isoWeek
    }
    println(result)
    return result
}

private fun adjustToIsoWeek(year: String, sqliteWeek: Int): Int {
    val jan1 = LocalDate.of(year.toInt(), 1, 1)
    val jan1DayOfWeek = jan1.dayOfWeek.value

    return if (jan1DayOfWeek <= 4) {
        sqliteWeek + 1
    } else {
        sqliteWeek
    }
}