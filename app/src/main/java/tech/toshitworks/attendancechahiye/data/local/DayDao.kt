package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import tech.toshitworks.attendancechahiye.data.entity.DayEntity

@Dao
interface DayDao {

    @Insert
    suspend fun insertDays(days: List<DayEntity>)

    @Query("SELECT * FROM days WHERE name = :name LIMIT 1")
    suspend fun getDayByName(name: String): DayEntity

    @Query("SELECT * FROM days")
    suspend fun getDays(): List<DayEntity>

    @Query("SELECT * FROM days WHERE id = :dayId LIMIT 1")
    suspend fun getDayById(dayId: Long): DayEntity

}