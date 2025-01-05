package tech.toshitworks.attendo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.data.entity.TimetableEntity

@Dao
interface TimetableDao {

    @Insert
    suspend fun insertPeriod(timetable: TimetableEntity)

    @Insert
    suspend fun insertTimetableForDay(timetables: List<TimetableEntity>)

    @Query("SELECT * FROM timetable WHERE subject_id = :subjectId AND day_id = :dayId AND period_id = :periodId")
    suspend fun getTimetable(subjectId: Long,dayId: Long,periodId: Long): TimetableEntity

    @Query("""
        SELECT * FROM timetable
        INNER JOIN days ON timetable.day_id = days.id
        WHERE days.name = :day
    """)
    fun getTimetableForDay(day: String): Flow<List<TimetableEntity>>

    @Update
    suspend fun updatePeriods(timetables: List<TimetableEntity>)

    @Delete
    suspend fun deletePeriod(timetable: TimetableEntity)

    @Query("SELECT * FROM timetable WHERE subject_id = :subjectId")
    suspend fun getPeriodsBySubject(subjectId: Long): List<TimetableEntity>

    @Query("SELECT * FROM timetable")
    suspend fun getAllPeriods(): List<TimetableEntity>

    @Query("SELECT * FROM timetable WHERE day_id = :dayId AND period_id = :periodId")
    suspend fun getDeletedPeriodsForDay(dayId: Long, periodId: Long): TimetableEntity

    @Query("UPDATE timetable SET deletedForDates = :deletedForDates WHERE day_id = :dayId AND period_id = :periodId")
    suspend fun updateDeletedPeriodsForDay(dayId: Long, periodId: Long, deletedForDates: List<String>)

    @Query("SELECT * FROM timetable WHERE day_id = :dayId AND period_id = :periodId")
    suspend fun getEditedPeriodsForDay(dayId: Long, periodId: Long): TimetableEntity

    @Query("UPDATE timetable SET editedForDates = :editedForDates WHERE day_id = :dayId AND period_id = :periodId")
    suspend fun updateEditedPeriodsForDay(dayId: Long, periodId: Long, editedForDates: List<String>)


}