package tech.toshitworks.attendancechahiye.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun attendanceRepository(): AttendanceRepository
}