package tech.toshitworks.attendancechahiye.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attendance",
    indices = [
        Index(value = ["period_id", "date"], unique = true),
        Index(value = ["subject_id"]),
        Index(value = ["day_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subject_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PeriodEntity::class,
            parentColumns = ["id"],
            childColumns = ["period_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["day_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "day_id") val dayId: Long,
    @ColumnInfo(name = "subject_id") val subjectId: Long,
    @ColumnInfo(name = "period_id") val periodId: Long,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "is_present") val isPresent: Boolean,
    @ColumnInfo(name = "deleted") val deleted: Boolean = false
)