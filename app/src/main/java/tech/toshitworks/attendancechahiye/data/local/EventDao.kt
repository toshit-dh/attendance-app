package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Query
import tech.toshitworks.attendancechahiye.data.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * from event")
    suspend fun getEvents(): List<EventEntity>

}