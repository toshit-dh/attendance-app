package tech.toshitworks.attendancechahiye.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.attendancechahiye.data.local.AttendanceDatabase
import javax.inject.Singleton

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Drop the old table
        database.execSQL("DROP TABLE IF EXISTS attendance")

        // Create the new table
        database.execSQL(
            """
            CREATE TABLE attendance (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject_id INTEGER NOT NULL,
                date TEXT NOT NULL,
                is_present INTEGER NOT NULL
            )
            """
        )
        database.execSQL("DELETE FROM attendance")

        // Add the unique index for (subject_id, date)
        database.execSQL(
            """
            CREATE UNIQUE INDEX index_attendance_subject_id_date
            ON attendance (subject_id, date)
            """
        )
    }
}

val MIGRATION_3_4 = object : Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Step 1: Rename the old table
        database.execSQL("ALTER TABLE attendance RENAME TO attendance_old")


        // Step 2: Create the new table with the updated schema
        database.execSQL("""
            CREATE TABLE attendance (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject_id INTEGER NOT NULL,
                period_id INTEGER NOT NULL,
                date TEXT NOT NULL,
                is_present INTEGER NOT NULL,
                FOREIGN KEY(subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
                FOREIGN KEY(period_id) REFERENCES periods(id) ON DELETE CASCADE,
                UNIQUE(period_id, date)
            )
        """)
        database.execSQL(
            "CREATE UNIQUE INDEX IF NOT EXISTS index_attendance_period_id_date ON attendance (period_id ASC, date ASC)"
        )

        // Step 4: Drop the old table
        database.execSQL("DROP TABLE attendance_old")
    }
}

val MIGRATION_4_5 = object : Migration(4,5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add index on subject_id column
        database.execSQL("CREATE INDEX IF NOT EXISTS index_attendance_subject_id ON attendance(subject_id)")
    }
}



@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAttendanceDatabase(
        @ApplicationContext context: Context
    ) : AttendanceDatabase
    = Room.databaseBuilder(
        context,
        AttendanceDatabase::class.java,
        AttendanceDatabase.DATABASE_NAME
    )
        .addMigrations(MIGRATION_2_3)
        .addMigrations(MIGRATION_3_4)
        .addMigrations(MIGRATION_4_5)
        .build()

    @Provides
    fun provideDaysDao(database: AttendanceDatabase) = database.daysDao()

    @Provides
    fun provideAttendanceDao(database: AttendanceDatabase) = database.attendanceDao()

    @Provides
    fun providePeriodDao(database: AttendanceDatabase) = database.periodDao()

    @Provides
    fun provideSubjectDao(database: AttendanceDatabase) = database.subjectDao()

    @Provides
    fun provideSemesterDao(database: AttendanceDatabase) = database.semesterDao()

    @Provides
    fun provideTimetableDao(database: AttendanceDatabase) = database.timetableDao()


}