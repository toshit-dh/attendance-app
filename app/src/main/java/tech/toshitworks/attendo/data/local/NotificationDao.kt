package tech.toshitworks.attendo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import tech.toshitworks.attendo.data.entity.NotificationEntity

@Dao
interface NotificationDao {


    @Insert
    suspend fun insertNotification(notificationEntity: NotificationEntity)

    @Query("""
        SELECT * FROM notification
    """)
    suspend fun getNotifications(): List<NotificationEntity>

}