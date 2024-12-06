package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.toshitworks.attendancechahiye.data.entity.AttendanceBySubject
import tech.toshitworks.attendancechahiye.data.entity.AttendanceEntity
import tech.toshitworks.attendancechahiye.data.entity.DayEntity
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
    ],
    version = 5,
    exportSchema = false
)

@Singleton
abstract class AttendanceDatabase : RoomDatabase() {

    abstract fun daysDao(): DayDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun periodDao(): PeriodDao
    abstract fun subjectDao(): SubjectDao
    abstract fun timetableDao(): TimetableDao
    abstract fun semesterDao(): SemesterDao

    companion object {
        const val DATABASE_NAME = "attendance_database"
    }
}
