package tech.toshitworks.attendancechahiye.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "subjects", indices = [Index(value = ["name"], unique = true)])
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val facultyName: String,
    val isAttendanceCounted: Boolean = true
)
