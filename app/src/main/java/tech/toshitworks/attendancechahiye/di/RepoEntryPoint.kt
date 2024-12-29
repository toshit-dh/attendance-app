package tech.toshitworks.attendancechahiye.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.EventRepository
import tech.toshitworks.attendancechahiye.domain.repository.NoteRepository
import tech.toshitworks.attendancechahiye.domain.repository.NotificationService
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepoEntryPoint {
    fun attendanceRepository(): AttendanceRepository
    fun subjectRepository(): SubjectRepository
    fun timetableRepository(): TimetableRepository
    fun notesRepository(): NoteRepository
    fun eventsRepository(): EventRepository
    fun notificationRepository(): NotificationService
}