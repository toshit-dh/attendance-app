package tech.toshitworks.attendo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.data.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * from event")
    fun getEvents(): Flow<List<EventEntity>>

    @Upsert
    suspend fun addEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)


}