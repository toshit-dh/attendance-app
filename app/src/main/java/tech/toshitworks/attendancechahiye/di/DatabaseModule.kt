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