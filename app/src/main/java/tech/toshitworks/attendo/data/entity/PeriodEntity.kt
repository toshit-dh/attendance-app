package tech.toshitworks.attendo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "periods")
data class PeriodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startTime: String,
    val endTime: String
)