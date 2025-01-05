package tech.toshitworks.attendo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.attendo.data.repository.AnalyticsRepoImpl
import tech.toshitworks.attendo.data.repository.AttendanceRepoImpl
import tech.toshitworks.attendo.domain.repository.CsvWorkRepository
import tech.toshitworks.attendo.data.repository.CsvWorkRepositoryImpl
import tech.toshitworks.attendo.data.repository.DataStoreRepoImpl
import tech.toshitworks.attendo.data.repository.DayRepositoryImpl
import tech.toshitworks.attendo.data.repository.EventRepoImpl
import tech.toshitworks.attendo.data.repository.MarkAttendanceImpl
import tech.toshitworks.attendo.data.repository.NoteRepositoryImpl
import tech.toshitworks.attendo.data.repository.NotificationWorkRepoImpl
import tech.toshitworks.attendo.data.repository.PeriodRepositoryImpl
import tech.toshitworks.attendo.data.repository.SemesterRepoImpl
import tech.toshitworks.attendo.data.repository.SubjectRepoImpl
import tech.toshitworks.attendo.data.repository.TimetableRepoImpl
import tech.toshitworks.attendo.domain.repository.AnalyticsRepository
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.EventRepository
import tech.toshitworks.attendo.domain.repository.MarkAttendance
import tech.toshitworks.attendo.domain.repository.NoteRepository
import tech.toshitworks.attendo.domain.repository.NotificationService
import tech.toshitworks.attendo.domain.repository.NotificationWorkRepository
import tech.toshitworks.attendo.domain.repository.PeriodRepository
import tech.toshitworks.attendo.domain.repository.SemesterRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import tech.toshitworks.attendo.domain.repository.TimetableRepository
import tech.toshitworks.attendo.services.NotificationServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(
        dataStoreRepoImpl: DataStoreRepoImpl
    ): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindAttendanceRepository(
        attendanceRepoImpl: AttendanceRepoImpl
    ): AttendanceRepository

    @Singleton
    @Binds
    abstract fun bindDayRepository(
        dayRepositoryImpl: DayRepositoryImpl
    ): DayRepository

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        subjectRepoImpl: SubjectRepoImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTimetableRepository(
        timetableRepoImpl: TimetableRepoImpl
    ): TimetableRepository

    @Singleton
    @Binds
    abstract fun bindPeriodRepository(
        periodRepoImpl: PeriodRepositoryImpl
    ): PeriodRepository

    @Singleton
    @Binds
    abstract fun bindSemesterRepository(
        semesterRepoImpl: SemesterRepoImpl
    ): SemesterRepository

    @Singleton
    @Binds
    abstract fun bindNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository

    @Singleton
    @Binds
    abstract fun bindAnalyticsRepository(
        analyticsRepoImpl: AnalyticsRepoImpl
    ): AnalyticsRepository

    @Singleton
    @Binds
    abstract fun bindEventRepository(
        eventRepoImpl: EventRepoImpl
    ): EventRepository

    @Singleton
    @Binds
    abstract fun bindMarkAttendance(
       markAttendance: MarkAttendanceImpl
    ): MarkAttendance

    @Singleton
    @Binds
    abstract fun bindCsvWorkRepository(
        csvWorkRepositoryImpl: CsvWorkRepositoryImpl
    ): CsvWorkRepository

    @Singleton
    @Binds
    abstract fun bindNotificationWorkRepository(
        notificationWorkRepositoryImpl: NotificationWorkRepoImpl
    ): NotificationWorkRepository

    @Singleton
    @Binds
    abstract fun bindNotificationRepository(
        notificationServiceImpl: NotificationServiceImpl
    ): NotificationService

}