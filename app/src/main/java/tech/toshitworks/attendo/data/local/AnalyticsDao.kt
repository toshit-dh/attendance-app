package tech.toshitworks.attendo.data.local

import androidx.room.Dao
import androidx.room.Query
import tech.toshitworks.attendo.data.dto.PeriodAnalysis
import tech.toshitworks.attendo.data.entity.AnalyticsByDay
import tech.toshitworks.attendo.data.entity.AnalyticsByMonth
import tech.toshitworks.attendo.data.entity.AnalyticsByWeek
import tech.toshitworks.attendo.data.entity.AnalyticsEntity

@Dao
interface AnalyticsDao {

    @Query("""
        SELECT a.period_id AS periodId
        ,p.startTime AS startTime,
        p.endTime AS endTime,
        SUM(CASE WHEN a.id AND isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesConducted, 
        SUM(CASE WHEN a.is_present AND isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        INNER JOIN subjects s ON s.id = a.subject_id
        INNER JOIN periods p ON p.id = a.period_id
        WHERE deleted = false AND s.isAttendanceCounted = true
        GROUP BY period_id
    """)
    suspend fun getPeriodAnalysis(): List<PeriodAnalysis>

    @Query(
        """
        SELECT
        s.id AS subjectId,
            d.id AS dayId, 
            d.name AS dayName, 
            SUM(CASE WHEN a.id AND isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesConducted, 
            SUM(CASE WHEN a.is_present AND isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        JOIN days d ON d.id = a.day_id
        WHERE a.deleted = false
        GROUP BY d.id
    """
    )
    suspend fun getAnalysisByDay(): List<AnalyticsByDay>

    @Query(
        """
        SELECT
        s.id AS subjectId,
            strftime('%Y-%W', a.date) AS week,
            SUM(CASE WHEN a.id IS NOT NULL AND s.isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesConducted, 
            SUM(CASE WHEN a.is_present = 1 AND s.isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        WHERE a.deleted = false
        GROUP BY strftime('%Y-%W', a.date)  
    """
    )
    suspend fun getAnalysisByWeek(): List<AnalyticsByWeek>

    @Query(
        """
        SELECT
        s.id AS subjectId,
            strftime('%Y-%m', a.date) AS month,  
            SUM(CASE WHEN a.id IS NOT NULL AND s.isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesConducted, 
            SUM(CASE WHEN a.is_present = 1 AND s.isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        WHERE a.deleted = false
        GROUP BY strftime('%Y-%m', a.date) 
    """
    )
    suspend fun getAnalysisByMonth(): List<AnalyticsByMonth>

    @Query(
        """
        SELECT
        s.id AS subjectId,
            SUM(CASE WHEN a.id AND isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesTaken, 
            SUM(CASE WHEN a.is_present AND isAttendanceCounted = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        WHERE a.deleted = false
    """
    )
    suspend fun getAnalysis(): AnalyticsEntity

    @Query(
        """
        SELECT
        s.id AS subjectId,
            d.id AS dayId, 
            d.name AS dayName, 
            COUNT(a.id) AS lecturesConducted, 
            SUM(CASE WHEN a.is_present = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        JOIN days d ON d.id = a.day_id
        WHERE s.name = :subjectName AND a.deleted = false
        GROUP BY d.id

    """
    )
    suspend fun getAnalysisOfSubjectByDay(subjectName: String): List<AnalyticsByDay>

    @Query(
        """
        SELECT
        s.id AS subjectId,
            strftime('%Y-%W', a.date) AS week,
            SUM(CASE WHEN a.id IS NOT NULL  THEN 1 ELSE 0 END) AS lecturesConducted, 
            SUM(CASE WHEN a.is_present = 1  THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        WHERE s.name = :subjectName AND a.deleted = false
        GROUP BY strftime('%Y-%W', a.date)  
    """
    )
    suspend fun getAnalysisOfSubjectByWeek(subjectName: String): List<AnalyticsByWeek>

    @Query(
        """
        SELECT
        s.id AS subjectId,
            strftime('%Y-%m', a.date) AS month,  
            SUM(CASE WHEN a.id IS NOT NULL THEN 1 ELSE 0 END) AS lecturesConducted, 
            SUM(CASE WHEN a.is_present = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        WHERE s.name = :subjectName AND a.deleted = false
        GROUP BY strftime('%Y-%m', a.date)
    """
    )
    suspend fun getAnalysisOfSubjectByMonth(subjectName: String): List<AnalyticsByMonth>

    @Query(
        """
        SELECT
            s.id AS subjectId,
            COUNT(a.id) AS lecturesTaken, 
            SUM(CASE WHEN a.is_present = 1 THEN 1 ELSE 0 END) AS lecturesPresent
        FROM attendance a
        JOIN subjects s ON s.id = a.subject_id
        WHERE s.name = :subjectName AND a.deleted = false
    """
    )
    suspend fun getAnalysisOfSubject(subjectName: String): AnalyticsEntity

}