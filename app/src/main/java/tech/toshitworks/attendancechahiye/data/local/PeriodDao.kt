package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import tech.toshitworks.attendancechahiye.data.entity.PeriodEntity

@Dao
interface PeriodDao {

    @Insert
    suspend fun insertPeriod(period: PeriodEntity)

    @Insert
    suspend fun insertPeriods(periods: List<PeriodEntity>)

    @Update
    suspend fun updatePeriod(period: PeriodEntity)

    @Query("SELECT * FROM periods")
    suspend fun getAllPeriods(): List<PeriodEntity>

    @Query("SELECT * FROM periods WHERE startTime = :startTime LIMIT 1")
    suspend fun getPeriodByStartTime(startTime: String): PeriodEntity

    @Query("SELECT * FROM periods WHERE id = :periodId LIMIT 1")
    suspend fun getPeriodById(periodId: Long): PeriodEntity


}