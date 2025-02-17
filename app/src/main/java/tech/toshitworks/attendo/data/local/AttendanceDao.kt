package tech.toshitworks.attendo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.data.dto.AttendanceBySubject
import tech.toshitworks.attendo.data.entity.AttendanceEntity
import tech.toshitworks.attendo.data.dto.AttendanceStats

@Dao
interface AttendanceDao {

    @Upsert
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Update
    suspend fun updateAttendance(attendance: AttendanceEntity)

    @Delete
    suspend fun deleteAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendance WHERE deleted = false")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance")
    fun getPlusDeletedAttendance(): Flow<List<AttendanceEntity>>



    @Query("""
        SELECT attendance.*
        FROM attendance
        INNER JOIN subjects ON attendance.subject_id = subjects.id
        WHERE subjects.name = :subjectName AND attendance.deleted = false
    """)
    fun getAttendanceBySubject(subjectName: String): Flow<List<AttendanceEntity>>

    @Query("""
        UPDATE attendance
        SET is_present = :isPresent, subject_id = :subjectId,deleted = :deleted
        WHERE date = :date AND period_id = :periodId
    """)
    suspend fun updateAttendanceByDate(date: String,isPresent: Boolean,deleted: Boolean,periodId: Long,subjectId: Long)

    @Query("SELECT * FROM attendance WHERE date BETWEEN :startDate AND :endDate AND deleted = false")
    fun getAttendanceByDateRange(startDate: String, endDate: String): Flow<List<AttendanceEntity>>

    @Query("""
    SELECT subjects.id AS subjectId,
           subjects.name AS subjectName,
           subjects.facultyName AS facultyName,
           subjects.isAttendanceCounted AS isAttendanceCounted,
           COUNT(CASE WHEN attendance.is_present = 1 THEN 1 END) AS lecturesPresent,
           COUNT(attendance.id) AS lecturesTaken
    FROM attendance
    INNER JOIN subjects ON attendance.subject_id = subjects.id
    WHERE deleted = false
    GROUP BY subjects.id
    
""")
    fun getOverallAttendance(): Flow<List<AttendanceBySubject>>

    @Query("""
        SELECT 
            COUNT(a.id) AS totalLectures,
            SUM(CASE WHEN a.is_present THEN 1 ELSE 0 END) AS totalPresent,
            (SUM(CASE WHEN a.is_present THEN 1 ELSE 0 END) * 100.0) / COUNT(a.id) AS attendancePercentage
        FROM 
            attendance a
        JOIN 
            subjects s ON a.subject_id = s.id
        WHERE 
            s.isAttendanceCounted = 1 AND deleted = false
""")
    fun getAttendancePercentage(): Flow<AttendanceStats>

    @Query("""
        SELECT s.id AS subjectId,
        s.name AS subjectName,
        s.facultyName AS facultyName,
        s.isAttendanceCounted AS isAttendanceCounted,
        COUNT(a.id) AS lecturesTaken,
        SUM(CASE WHEN a.is_present THEN 1 ELSE 0 END) AS lecturesPresent
        FROM subjects s
        INNER JOIN attendance a
        ON s.id = a.subject_id
        WHERE s.isAttendanceCounted = 1 AND deleted = false
        GROUP BY s.id
    """)
    fun getAttendancePercentageBySubject(): Flow<List<AttendanceBySubject>>


    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): Flow<List<AttendanceEntity>>

    @Query("DELETE FROM attendance")
    suspend fun deleteAllAttendance()
}


