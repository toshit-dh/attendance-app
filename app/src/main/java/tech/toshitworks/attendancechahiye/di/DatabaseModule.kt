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
    override fun migrate(db: SupportSQLiteDatabase) {
        // Drop the old table
        db.execSQL("DROP TABLE IF EXISTS attendance")

        // Create the new table
        db.execSQL(
            """
            CREATE TABLE attendance (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject_id INTEGER NOT NULL,
                date TEXT NOT NULL,
                is_present INTEGER NOT NULL
            )
            """
        )
        db.execSQL("DELETE FROM attendance")

        // Add the unique index for (subject_id, date)
        db.execSQL(
            """
            CREATE UNIQUE INDEX index_attendance_subject_id_date
            ON attendance (subject_id, date)
            """
        )
    }
}

val MIGRATION_3_4 = object : Migration(3,4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Step 1: Rename the old table
        db.execSQL("ALTER TABLE attendance RENAME TO attendance_old")


        // Step 2: Create the new table with the updated schema
        db.execSQL("""
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
        db.execSQL(
            "CREATE UNIQUE INDEX IF NOT EXISTS index_attendance_period_id_date ON attendance (period_id ASC, date ASC)"
        )

        // Step 4: Drop the old table
        db.execSQL("DROP TABLE attendance_old")
    }
}

val MIGRATION_4_5 = object : Migration(4,5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add index on subject_id column
        db.execSQL("CREATE INDEX IF NOT EXISTS index_attendance_subject_id ON attendance(subject_id)")
    }
}
val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Creating the new table for `note` entity
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `note` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `content` TEXT NOT NULL, 
                `attendance_id` INTEGER NOT NULL, 
                FOREIGN KEY(`attendance_id`) REFERENCES `attendance`(`id`) ON DELETE CASCADE
            );
            """
        )

        // Creating the index for `attendance_id` in the `note` table
        db.execSQL(
            "CREATE UNIQUE INDEX IF NOT EXISTS `index_note_attendance_id` ON `note` (`attendance_id`)"
        )
    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Rename the old table
        db.execSQL("ALTER TABLE attendance RENAME TO attendance_temp")

        // Create the new table with correct schema
        db.execSQL(
            """
            CREATE TABLE attendance (
                id INTEGER NOT NULL PRIMARY KEY,
                subject_id INTEGER NOT NULL,
                period_id INTEGER NOT NULL,
                date TEXT NOT NULL,
                is_present INTEGER NOT NULL,
                day_id INTEGER NOT NULL,
                FOREIGN KEY(day_id) REFERENCES days(id) ON DELETE CASCADE ON UPDATE NO ACTION,
                FOREIGN KEY(period_id) REFERENCES periods(id) ON DELETE CASCADE ON UPDATE NO ACTION,
                FOREIGN KEY(subject_id) REFERENCES subjects(id) ON DELETE CASCADE ON UPDATE NO ACTION
            )
            """
        )

        // Migrate data from old table to new table


        // Drop the old table
        db.execSQL("DROP TABLE attendance_temp")

        // Recreate indices with the correct properties
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_attendance_period_id_date ON attendance(period_id, date)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_attendance_subject_id ON attendance(subject_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_attendance_day_id ON attendance(day_id)")
    }
}

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create the new `event` table with the foreign key and index
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `event` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `date` TEXT NOT NULL, 
                `subject_id` INTEGER NOT NULL, 
                FOREIGN KEY(`subject_id`) REFERENCES `subjects`(`id`) ON DELETE CASCADE
            )
            """
        )

        // Add an index on the `subject_id` column
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_event_subject_id` ON `event`(`subject_id`)")
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
        .addMigrations(MIGRATION_5_6)
        .addMigrations(MIGRATION_6_7)
        .addMigrations(MIGRATION_7_8)
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

    @Provides
    fun provideNoteDao(database: AttendanceDatabase) = database.noteDao()

    @Provides
    fun provideAnalyticsDao(database: AttendanceDatabase) = database.analyticsDao()


}