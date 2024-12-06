package tech.toshitworks.attendancechahiye.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semester")
data class SemesterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val semNumber: Int,
    val midTermDate: String? = null,
    val startDate: String,
    val endDate: String? = null
)
