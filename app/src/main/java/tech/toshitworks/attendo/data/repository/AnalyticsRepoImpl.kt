package tech.toshitworks.attendo.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import tech.toshitworks.attendo.data.local.AnalyticsDao
import tech.toshitworks.attendo.domain.model.AnalyticsByDay
import tech.toshitworks.attendo.domain.model.AnalyticsByMonth
import tech.toshitworks.attendo.domain.model.AnalyticsByWeek
import tech.toshitworks.attendo.domain.model.AnalyticsModel
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.EligibilityData
import tech.toshitworks.attendo.domain.model.PeriodAnalysis
import tech.toshitworks.attendo.domain.repository.AnalyticsRepository
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AnalyticsRepoImpl @Inject constructor(
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository,
    private val analyticsDao: AnalyticsDao,
    private val attendanceRepository: AttendanceRepository
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
        return arrayOf(midTermResult, endSemResult)
    }

    private fun calculateStreak(attendanceList: List<AttendanceModel>): Pair<Int,Int> {
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
        return Pair(current,longest)
    }

    private fun combineWeeks(analyticsList: List<AnalyticsByWeek>): List<AnalyticsByWeek> {
        val result = mutableListOf<AnalyticsByWeek>()
        var previousWeek: AnalyticsByWeek? = null
        var isChanged = false
        for (i in analyticsList.indices) {
            val current = analyticsList[i]
            val currentYear = current.yearWeek.substring(0, 4)
            val currentWeek = current.yearWeek.substring(5).toInt()
            if (currentWeek == 0 && previousWeek != null) {
                result[result.lastIndex] = result.last().copy(
                    lecturesConducted = previousWeek.lecturesConducted + current.lecturesConducted,
                    lecturesPresent = previousWeek.lecturesPresent + current.lecturesPresent,
                    yearWeek = "$currentYear-01"
                )
                isChanged = true
            } else if (isChanged){
                val incrementedWeek = "$currentYear-${(current.yearWeek.substring(5).toInt()+1).toString().padStart(2, '0')}"
                result.add(current.copy(
                    yearWeek = incrementedWeek
                ))
            }
            else{
                result.add(current)
            }
            previousWeek = current
        }
        return result
    }

    override suspend fun getAnalysis(
        startDate: String,
        midTermDate: String?,
        endSemDate: String?
    ): List<AnalyticsModel> = coroutineScope {
        val subjects = async { subjectRepository.getSubjects() }.await().dropLastWhile {
            it.name == "Lunch" || it.name == "No Period"
        }
        val periodAnalysisD = async { analyticsDao.getPeriodAnalysis() }
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
            val subjectsAnalysis = subjectsAnalysisD.awaitAll()
            val subjectsAnalysisByDay = subjectsAnalysisByDayD.awaitAll()
            val subjectsAnalysisByWeek = subjectsAnalysisByWeekD.awaitAll()
            val subjectsAnalysisByMonth = subjectsAnalysisByMonthD.awaitAll()
            val dayMap = days.associateBy { it.id }
            val subjectMap = subjects.associateBy { it.id }
            val midTermMap = countDaysBetweenDates(todayDate, midTermDate ?: startDate)
            val endSemMap = countDaysBetweenDates(todayDate, endSemDate ?: startDate)
            val subjectAnalysisList = subjects.mapIndexed { idx, it ->
                val attendanceBySubject =
                    attendanceRepository.getAttendanceBySubject(it.name).first()
                val streak = calculateStreak(attendanceBySubject)
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
                    analysisByWeek = combineWeeks(sABW.map {
                        AnalyticsByWeek(
                            yearWeek = it.yearWeek,
                            lecturesConducted = it.lecturesConducted,
                            lecturesPresent = it.lecturesPresent
                        )
                    }),
                    analysisByMonth = sABM.map {
                        AnalyticsByMonth(
                            yearMonth = it.yearMonth,
                            lecturesConducted = it.lecturesConducted,
                            lecturesPresent = it.lecturesPresent
                        )
                    },
                    streak = streak,
                    eligibilityOfMidterm = eligibilityOfMidterm,
                    eligibilityOfEndSem = eligibilityOfEndSem
                )
            }
            val periodAnalysis = periodAnalysisD.await()
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
                    analysisByWeek = combineWeeks(analyticsByWeek.map {
                        AnalyticsByWeek(
                            yearWeek = it.yearWeek,
                            lecturesConducted = it.lecturesConducted,
                            lecturesPresent = it.lecturesPresent
                        )
                    }),
                    analysisByMonth = analyticsByMonth.map {
                        AnalyticsByMonth(
                            yearMonth = it.yearMonth,
                            lecturesConducted = it.lecturesConducted,
                            lecturesPresent = it.lecturesPresent
                        )
                    },
                    streak = null,
                    periodAnalysis = periodAnalysis.map {
                        PeriodAnalysis(
                            periodId = it.periodId,
                            startTime = it.startTime,
                            endTime = it.endTime,
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