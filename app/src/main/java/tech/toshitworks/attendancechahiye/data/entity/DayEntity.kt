package tech.toshitworks.attendancechahiye.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
