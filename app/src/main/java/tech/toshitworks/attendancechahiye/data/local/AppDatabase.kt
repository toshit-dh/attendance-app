package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.toshitworks.attendancechahiye.data.converters.Converters
import tech.toshitworks.attendancechahiye.data.entity.AttendanceEntity
import tech.toshitworks.attendancechahiye.data.entity.DayEntity
import tech.toshitworks.attendancechahiye.data.entity.EventEntity
import tech.toshitworks.attendancechahiye.data.entity.NoteEntity
import tech.toshitworks.attendancechahiye.data.entity.PeriodEntity
import tech.toshitworks.attendancechahiye.data.entity.SubjectEntity
import tech.toshitworks.attendancechahiye.data.entity.TimetableEntity
import tech.toshitworks.attendancechahiye.data.entity.SemesterEntity
import javax.inject.Singleton

@Database(
    entities = [
        DayEntity::class,
        AttendanceEntity::class,
        PeriodEntity::class,
        SubjectEntity::class,
        TimetableEntity::class,
        SemesterEntity::class,
        NoteEntity::class,
        EventEntity::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
@Singleton
abstract class AttendanceDatabase : RoomDatabase() {

    abstract fun daysDao(): DayDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun periodDao(): PeriodDao
    abstract fun subjectDao(): SubjectDao
    abstract fun timetableDao(): TimetableDao
    abstract fun semesterDao(): SemesterDao
    abstract fun noteDao(): NoteDao
    abstract fun analyticsDao(): AnalyticsDao
    abstract fun eventDao(): EventDao
    companion object {
        const val DATABASE_NAME = "attendance_database"
    }
}
