package tech.toshitworks.attendo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "note",
    indices = [
        Index(value = ["attendance_id"], unique = true),
    ],
    foreignKeys = [
        ForeignKey(
            entity = AttendanceEntity::class,
            parentColumns = ["id"],
            childColumns = ["attendance_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "attendance_id") val attendanceId: Long
)