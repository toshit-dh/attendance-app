package tech.toshitworks.attendancechahiye.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "event",
    indices = [
        Index(value = ["subject_id"]),
    ],
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subject_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    @ColumnInfo(name = "subject_id") val subjectId: Long,
    val content: String
)