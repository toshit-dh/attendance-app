package tech.toshitworks.attendancechahiye.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import tech.toshitworks.attendancechahiye.domain.model.EligibilityData
import tech.toshitworks.attendancechahiye.data.local.AnalyticsDao
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByDay
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByMonth
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByWeek
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsModel
import tech.toshitworks.attendancechahiye.domain.repository.AnalyticsRepository
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AnalyticsRepoImpl @Inject constructor(
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository,
    private val analyticsDao: AnalyticsDao
) : AnalyticsRepository {
    private fun countDaysBetweenDates(startDate: String, endDate: String): Map<String, Int> {
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

    private fun calculateEligibility(
        lecturesConducted: Int,
        lecturesPresent: Int,
        totalCountMidTerm: Int,
        totalCountEndSem: Int,
    ): Array<EligibilityData> {
        val totalLecturesTillMidTerm = lecturesConducted + totalCountMidTerm
        val totalLecturesTillEndSem = lecturesConducted + totalCountEndSem
        println(totalLecturesTillEndSem)
        val percentageAttendedMidTerm =
            (lecturesPresent.toFloat() / lecturesConducted.toFloat()) * 100
        val percentageAttendedEndSem =
            (lecturesPresent.toFloat() / lecturesConducted.toFloat()) * 100
        val midTermEligible = percentageAttendedMidTerm >= 50
        val endSemEligible = percentageAttendedEndSem >= 75
        val midTermResult = if (midTermEligible) {
            val remainingLecturesMidTerm =
                (totalLecturesTillMidTerm * 0.50).toInt() - lecturesPresent
            val bunkLectures = totalCountMidTerm - remainingLecturesMidTerm
            EligibilityData(
                isEligibleForNow = true,
                isEligibleInFuture = true,
                bunkLectures = bunkLectures,
                moreLectures = remainingLecturesMidTerm
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
                    moreLectures = -1
                )
            } else {
                EligibilityData(
                    isEligibleForNow = false,
                    isEligibleInFuture = true,
                    bunkLectures = bunkLectures,
                    moreLectures = remainingLecturesMidTerm
                )
            }
        }
        val endSemResult = if (endSemEligible) {
            val remainingLecturesEndSem = (totalLecturesTillEndSem * 0.75).toInt() - lecturesPresent
            val bunkLectures = totalCountEndSem - remainingLecturesEndSem
            EligibilityData(
                isEligibleForNow = true,
                isEligibleInFuture = true,
                bunkLectures = bunkLectures,
                moreLectures = remainingLecturesEndSem
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
                    moreLectures = -1
                )
            } else EligibilityData(
                isEligibleForNow = false,
                isEligibleInFuture = true,
                bunkLectures = bunkLectures,
                moreLectures = remainingLecturesEndSem
            )
        }
        return arrayOf(midTermResult,endSemResult)
    }

        override suspend fun getAnalysis(
            startDate: String,
            midTermDate: String?,
            endSemDate: String?
        ): List<AnalyticsModel> = coroutineScope {
            val subjects = async { subjectRepository.getSubjects() }.await().dropLastWhile {
                it.name == "Lunch" || it.name == "No Period"
            }
            val days = async { dayRepository.getDays() }.await()
            val analyticsD = async { analyticsDao.getAnalysis() }
            val analyticsByDayD = async { analyticsDao.getAnalysisByDay() }
            val analyticsByWeekD = async { analyticsDao.getAnalysisByWeek() }
            val analyticsByMonthD = async { analyticsDao.getAnalysisByMonth() }

            val subjectsAnalysisD = subjects.map {
                async { analyticsDao.getAnalysisOfSubject(it.name) }
            }
            val subjectsAnalysisByDayD = subjects.map {
                async { analyticsDao.getAnalysisOfSubjectByDay(it.name) }
            }
            val subjectsAnalysisByWeekD = subjects.map {
                async { analyticsDao.getAnalysisOfSubjectByWeek(it.name) }
            }
            val subjectsAnalysisByMonthD = subjects.map {
                async { analyticsDao.getAnalysisOfSubjectByMonth(it.name) }
            }

            try {
                val today: LocalDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val todayDate = today.format(formatter)
                val analytics = analyticsD.await()
                val analyticsByDay = analyticsByDayD.await()
                val analyticsByWeek = analyticsByWeekD.await()
                val analyticsByMonth = analyticsByMonthD.await()
                val subjectsAnalysis = subjectsAnalysisD.map { it.await() }
                val subjectsAnalysisByDay = subjectsAnalysisByDayD.map { it.await() }
                val subjectsAnalysisByWeek = subjectsAnalysisByWeekD.map { it.await() }
                val subjectsAnalysisByMonth = subjectsAnalysisByMonthD.map { it.await() }
                val dayMap = days.associateBy { it.id }
                val subjectMap = subjects.associateBy { it.id }
                val midTermMap = countDaysBetweenDates(todayDate, midTermDate ?: startDate)
                val endSemMap = countDaysBetweenDates(todayDate, endSemDate ?: startDate)
                val subjectAnalysisList = subjects.mapIndexed { idx, it ->
                    val daysS = subjectRepository.getDays(it.id)
                    val totalCountMidTerm = daysS.sumOf {
                        midTermMap[it.lowercase()] ?: 0
                    }
                    val totalCountEndSem = daysS.sumOf {
                        endSemMap[it.lowercase()] ?: 0
                    }
                    val eligibility = calculateEligibility(
                        lecturesConducted = subjectsAnalysis[idx].lecturesTaken,
                        lecturesPresent = subjectsAnalysis[idx].lecturesPresent,
                        totalCountMidTerm = totalCountMidTerm,
                        totalCountEndSem = totalCountEndSem
                    )
                    val eligibilityOfMidterm = if (midTermDate == null) {
                        null
                    } else {
                        eligibility[0]
                    }
                    val eligibilityOfEndSem = if (endSemDate == null) {
                        null
                    } else {
                        eligibility[1]
                    }
                    val sA = subjectsAnalysis[idx]
                    val sABD = subjectsAnalysisByDay[idx]
                    val sABW = subjectsAnalysisByWeek[idx]
                    val sABM = subjectsAnalysisByMonth[idx]
                    AnalyticsModel(
                        subject = subjectMap[it.id],
                        lecturesConducted = sA.lecturesTaken,
                        lecturesPresent = sA.lecturesPresent,
                        totalHours = sA.lecturesTaken,
                        analyticsByDay = sABD.map {
                            AnalyticsByDay(
                                day = dayMap[it.dayId]
                                    ?: throw IllegalArgumentException("Day not found"),
                                lecturesConducted = it.lecturesConducted,
                                lecturesPresent = it.lecturesPresent
                            )
                        },
                        analysisByWeek = sABW.map {
                            AnalyticsByWeek(
                                yearWeek = it.yearWeek,
                                lecturesConducted = it.lecturesConducted,
                                lecturesPresent = it.lecturesPresent
                            )
                        },
                        analysisByMonth = sABM.map {
                            AnalyticsByMonth(
                                yearMonth = it.yearMonth,
                                lecturesConducted = it.lecturesConducted,
                                lecturesPresent = it.lecturesPresent
                            )
                        },
                        eligibilityOfMidterm = eligibilityOfMidterm,
                        eligibilityOfEndSem = eligibilityOfEndSem
                    )
                }

                listOf(
                    AnalyticsModel(
                        subject = null,
                        lecturesConducted = analytics.lecturesTaken,
                        lecturesPresent = analytics.lecturesPresent,
                        totalHours = analytics.lecturesTaken,
                        analyticsByDay = analyticsByDay.map {
                            AnalyticsByDay(
                                day = dayMap[it.dayId]
                                    ?: throw IllegalArgumentException("Day not found"),
                                lecturesConducted = it.lecturesConducted,
                                lecturesPresent = it.lecturesPresent
                            )
                        },
                        analysisByWeek = analyticsByWeek.map {
                            AnalyticsByWeek(
                                yearWeek = it.yearWeek,
                                lecturesConducted = it.lecturesConducted,
                                lecturesPresent = it.lecturesPresent
                            )
                        },
                        analysisByMonth = analyticsByMonth.map {
                            AnalyticsByMonth(
                                yearMonth = it.yearMonth,
                                lecturesConducted = it.lecturesConducted,
                                lecturesPresent = it.lecturesPresent
                            )
                        },
                        eligibilityOfMidterm = null,
                        eligibilityOfEndSem = null
                    ),
                    *subjectAnalysisList.toTypedArray()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                listOf()
            }
        }


    }