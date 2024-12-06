package tech.toshitworks.attendancechahiye.domain.repository

import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

interface TimetableRepository {


    suspend fun insertPeriod(period: TimetableModel)

    suspend fun insertTimetable(timetables: List<TimetableModel?>)

    suspend fun getTimetableForDay(dayModel: DayModel): List<TimetableModel>

    suspend fun updatePeriod(period: TimetableModel)

    suspend fun deletePeriod(period: TimetableModel)

    suspend fun getPeriodsBySubject(subject: SubjectModel): List<TimetableModel>

    suspend fun getAllPeriods(): List<TimetableModel>
}