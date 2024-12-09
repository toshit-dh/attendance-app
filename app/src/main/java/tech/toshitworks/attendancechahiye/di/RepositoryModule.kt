package tech.toshitworks.attendancechahiye.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.attendancechahiye.data.repository.AnalyticsRepoImpl
import tech.toshitworks.attendancechahiye.data.repository.AttendanceRepoImpl
import tech.toshitworks.attendancechahiye.data.repository.DataStoreRepoImpl
import tech.toshitworks.attendancechahiye.data.repository.DayRepositoryImpl
import tech.toshitworks.attendancechahiye.data.repository.MarkAttendanceImpl
import tech.toshitworks.attendancechahiye.data.repository.NoteRepositoryImpl
import tech.toshitworks.attendancechahiye.data.repository.PeriodRepositoryImpl
import tech.toshitworks.attendancechahiye.data.repository.SemesterRepoImpl
import tech.toshitworks.attendancechahiye.data.repository.SubjectRepoImpl
import tech.toshitworks.attendancechahiye.data.repository.TimetableRepoImpl
import tech.toshitworks.attendancechahiye.domain.repository.AnalyticsRepository
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.DataStoreRepository
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.MarkAttendance
import tech.toshitworks.attendancechahiye.domain.repository.NoteRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
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
    abstract fun bindMarkAttendance(
       markAttendance: MarkAttendanceImpl
    ): MarkAttendance


}