package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import tech.toshitworks.attendancechahiye.data.entity.DayEntity
import tech.toshitworks.attendancechahiye.data.entity.SubjectEntity

@Dao
interface SubjectDao {

    @Insert
    suspend fun insertSubject(subject: SubjectEntity)

    @Insert
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    @Update
    suspend fun updateSubject(subject: SubjectEntity)

    @Query("SELECT * FROM subjects WHERE name = :name LIMIT 1")
    suspend fun getSubjectByName(name: String): SubjectEntity?

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<SubjectEntity>

    @Query("SELECT * FROM subjects WHERE id = :subjectId LIMIT 1")
    fun getSubjectById(subjectId: Long): SubjectEntity

//    @Query("""
//        SELECT d.name FROM
//        timetable t INNER JOIN days d
//        WHERE t.day_id = d.id AND t.subject_id = :subjectId
//    """)
//    suspend fun getLecturesByDays(subjectId: Long): List<DayEntity>

}