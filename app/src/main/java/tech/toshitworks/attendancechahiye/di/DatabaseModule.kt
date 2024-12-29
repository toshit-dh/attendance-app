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

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Adding the new column 'deleted' with default value false
        db.execSQL("ALTER TABLE attendance ADD COLUMN deleted INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add the new column with a default value to avoid breaking existing rows
        db.execSQL("ALTER TABLE event ADD COLUMN content TEXT NOT NULL DEFAULT ''")
    }
}

val MIGRATION_3_4 = object : Migration(3,4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE event_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                date TEXT NOT NULL,
                subject_id INTEGER NOT NULL,
                content TEXT NOT NULL,
                notificationWorkId BLOB,
                FOREIGN KEY(subject_id) REFERENCES subjects(id) ON DELETE CASCADE
            )
        """)


        // Drop the old table
        db.execSQL("DROP TABLE event")

        // Rename the new table to the original table name
        db.execSQL("ALTER TABLE event_new RENAME TO event")

        db.execSQL("CREATE INDEX IF NOT EXISTS index_event_subject_id ON event(subject_id)")
    }
}


val MIGRATION_4_5 = object : Migration(4,5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create a new temporary table with the updated schema
        db.execSQL("""
            CREATE TABLE event_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                date TEXT NOT NULL,
                subject_id INTEGER NOT NULL,
                content TEXT NOT NULL,
                notificationWorkId TEXT,
                FOREIGN KEY(subject_id) REFERENCES subjects(id) ON DELETE CASCADE
            )
        """)


        // Drop the old table
        db.execSQL("DROP TABLE event")

        // Rename the new table to the original table name
        db.execSQL("ALTER TABLE event_new RENAME TO event")

        db.execSQL("CREATE INDEX IF NOT EXISTS index_event_subject_id ON event(subject_id)")
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
        .addMigrations(MIGRATION_1_2)
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

    @Provides
    fun provideNoteDao(database: AttendanceDatabase) = database.noteDao()

    @Provides
    fun provideAnalyticsDao(database: AttendanceDatabase) = database.analyticsDao()

    @Provides
    fun providesEventDao(database: AttendanceDatabase) = database.eventDao()


}