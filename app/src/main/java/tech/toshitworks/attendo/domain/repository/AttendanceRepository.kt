package tech.toshitworks.attendo.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.domain.model.AttendanceBySubject
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.AttendanceStats

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: AttendanceModel)

    suspend fun updateAttendance(attendance: AttendanceModel)

    suspend fun updateAttendanceByDate(attendance: AttendanceModel)

    fun getAllAttendance(): Flow<List<AttendanceModel>>

    fun getPlusDeletedAttendance(): Flow<List<AttendanceModel>>

    fun getAttendancePercentage(): Flow<AttendanceStats>

    fun getAttendancePercentageBySubject(): Flow<List<AttendanceBySubject>>

    suspend fun deleteAttendance(attendance: AttendanceModel)

    fun getAttendanceByDate(date: String): Flow<List<AttendanceModel>>

    fun getAttendanceBySubject(subjectName: String): Flow<List<AttendanceModel>>

    fun getAttendanceByDateRange(startDate: String, endDate: String): Flow<List<AttendanceModel>>

    fun getOverallAttendance(): Flow<List<AttendanceBySubject>>

    suspend fun deleteAllAttendance()

}