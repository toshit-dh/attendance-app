package tech.toshitworks.attendancechahiye.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import tech.toshitworks.attendancechahiye.data.converters.Converters

@Entity(
    tableName = "timetable",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subject_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["day_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PeriodEntity::class,
            parentColumns = ["id"],
            childColumns = ["period_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("subject_id"), Index("day_id"), Index("period_id")]
)
@TypeConverters(Converters::class)
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "subject_id") val subjectId: Long,
    @ColumnInfo(name = "day_id") val dayId: Long,
    @ColumnInfo(name = "period_id") val periodId: Long,
    @ColumnInfo(name = "deletedForDates") val deletedForDates: List<String>? = null,
    @ColumnInfo(name = "editedForDates") val editedForDates: List<String>? = null
)