package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import tech.toshitworks.attendancechahiye.data.entity.TimetableEntity

@Dao
interface TimetableDao {

    @Insert
    suspend fun insertPeriod(timetable: TimetableEntity)

    @Insert
    suspend fun insertTimetableForDay(timetables: List<TimetableEntity>)

    @Query("SELECT * FROM timetable WHERE subject_id = :subjectId AND day_id = :dayId AND period_id = :periodId")
    suspend fun getTimetable(subjectId: Long,dayId: Long,periodId: Long): TimetableEntity

    @Query("SELECT * FROM timetable WHERE day_id = :dayId")
    suspend fun getTimetableForDay(dayId: Long): List<TimetableEntity>

    @Update
    suspend fun updatePeriod(timetable: TimetableEntity)

    @Delete
    suspend fun deletePeriod(timetable: TimetableEntity)

    @Query("SELECT * FROM timetable WHERE subject_id = :subjectId")
    suspend fun getPeriodsBySubject(subjectId: Long): List<TimetableEntity>

    @Query("SELECT * FROM timetable")
    suspend fun getAllPeriods(): List<TimetableEntity>


}