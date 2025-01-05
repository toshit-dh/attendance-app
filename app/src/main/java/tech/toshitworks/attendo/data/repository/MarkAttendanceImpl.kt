package tech.toshitworks.attendo.data.repository

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import tech.toshitworks.attendo.domain.repository.MarkAttendance
import tech.toshitworks.attendo.workers.MarkAttendanceWorker
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MarkAttendanceImpl @Inject constructor(
    private val workManager: WorkManager
): MarkAttendance {
    private fun calculateDelayFor1155PM(): Long {
        val currentTime = LocalTime.now()
        val targetTime = LocalTime.of(23, 55)
        val durationUntilTarget = if (currentTime.isBefore(targetTime)) {
            Duration.between(currentTime, targetTime)
        } else {
            Duration.between(currentTime, targetTime.plusHours(24))
        }
        return durationUntilTarget.toMillis()
    }
    override fun markAttendance() {
        val markAttendanceWorkRequest = PeriodicWorkRequestBuilder<MarkAttendanceWorker>(24,TimeUnit.HOURS)
            .setInitialDelay(calculateDelayFor1155PM(),TimeUnit.MILLISECONDS)
        workManager.enqueue(markAttendanceWorkRequest.build())
    }
}