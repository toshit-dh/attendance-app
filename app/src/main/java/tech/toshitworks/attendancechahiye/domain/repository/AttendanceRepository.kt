package tech.toshitworks.attendancechahiye.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendancechahiye.domain.model.AttendanceBySubject
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.AttendanceStats

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: AttendanceModel)

    suspend fun updateAttendance(attendance: AttendanceModel)

    suspend fun updateAttendanceByDate(attendance: AttendanceModel)

    suspend fun getAllAttendance(): List<AttendanceModel>

    fun getAttendancePercentage(): Flow<AttendanceStats>

    suspend fun deleteAttendance(attendance: AttendanceModel)

    fun getAttendanceByDate(date: String): Flow<List<AttendanceModel>>

    fun getAttendanceBySubject(subjectName: String): Flow<List<AttendanceModel>>

    fun getAttendanceByDateRange(startDate: String, endDate: String): Flow<List<AttendanceModel>>

    fun getOverallAttendance(): Flow<List<AttendanceBySubject>>

    suspend fun deleteAllAttendance()

}