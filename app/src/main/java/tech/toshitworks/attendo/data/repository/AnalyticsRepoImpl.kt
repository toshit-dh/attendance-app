package tech.toshitworks.attendo.data.repository

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import tech.toshitworks.attendo.data.local.AnalyticsDao
import tech.toshitworks.attendo.domain.model.AnalyticsByDay
import tech.toshitworks.attendo.domain.model.AnalyticsByMonth
import tech.toshitworks.attendo.domain.model.AnalyticsByWeek
import tech.toshitworks.attendo.domain.model.AnalyticsModel
import tech.toshitworks.attendo.domain.model.PeriodAnalysis
import tech.toshitworks.attendo.domain.repository.AnalyticsRepository
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import tech.toshitworks.attendo.utils.calculateEligibility
import tech.toshitworks.attendo.utils.calculateStreak
import tech.toshitworks.attendo.utils.combineWeeks
import tech.toshitworks.attendo.utils.countDaysBetweenDates
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AnalyticsRepoImpl @Inject constructor(
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository,
    private val analyticsDao: AnalyticsDao,
    private val attendanceRepository: AttendanceRepository
) : AnalyticsRepository {

    override suspend fun getAnalysis(
        startDate: String,
        midTermDate: String?,
        endSemDate: String?
    ): List<AnalyticsModel> = coroutineScope {
        try {
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
            Log.e("AnalyticsRepoImpl", "getAnalysis: ${e.message}")
            e.printStackTrace()
            listOf()
        }
    }
}