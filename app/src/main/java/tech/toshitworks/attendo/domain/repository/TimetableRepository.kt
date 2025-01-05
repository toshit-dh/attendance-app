package tech.toshitworks.attendo.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.model.TimetableModel

interface TimetableRepository {


    suspend fun insertPeriod(period: TimetableModel)

    suspend fun insertTimetable(timetables: List<TimetableModel?>)

    fun getTimetableForDay(day: String): Flow<List<TimetableModel>>

    suspend fun updatePeriods(timetables: List<TimetableModel>)

    suspend fun deletePeriod(period: TimetableModel)

    suspend fun getPeriodsBySubject(subject: SubjectModel): List<TimetableModel>

    suspend fun getAllPeriods(): List<TimetableModel>

    suspend fun deletePeriodForADate(timetable: TimetableModel,date: String)

    suspend fun editPeriodForADate(timetable: TimetableModel,date: String)


}