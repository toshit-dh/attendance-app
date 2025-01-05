package tech.toshitworks.attendo.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.EventRepository
import tech.toshitworks.attendo.domain.repository.NoteRepository
import tech.toshitworks.attendo.domain.repository.NotificationService
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import tech.toshitworks.attendo.domain.repository.TimetableRepository

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