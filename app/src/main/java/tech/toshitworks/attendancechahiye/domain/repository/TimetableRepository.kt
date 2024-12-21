package tech.toshitworks.attendancechahiye.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

interface TimetableRepository {


    suspend fun insertPeriod(period: TimetableModel)

    suspend fun insertTimetable(timetables: List<TimetableModel?>)

    fun getTimetableForDay(day: String): Flow<List<TimetableModel>>

    suspend fun updatePeriods(timetables: List<TimetableModel>)

    suspend fun deletePeriod(period: TimetableModel)

    suspend fun getPeriodsBySubject(subject: SubjectModel): List<TimetableModel>

    suspend fun getAllPeriods(): List<TimetableModel>

    suspend fun deletePeriodForADate(dayModel: DayModel, periodModel: TimetableModel,date: String)

    suspend fun editPeriodForADate(timetable: TimetableModel,date: String)


}