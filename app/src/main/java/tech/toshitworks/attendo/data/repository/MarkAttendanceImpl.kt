package tech.toshitworks.attendo.data.repository

import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import tech.toshitworks.attendo.domain.repository.MarkAttendance
import tech.toshitworks.attendo.workers.MarkAttendanceWorker
import java.time.Duration
import java.time.LocalTime
import java.util.UUID
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
    override fun markAttendance(channelId: String): UUID? {
        val inputData = workDataOf(
            "channelId" to channelId,
        )
        val initialDelay = calculateDelayFor1155PM()
        if (initialDelay <= 0) {
            Log.e("MarkAttendanceImpl", "Calculated delay is invalid: $initialDelay")
            return null
        }
        val markAttendanceWorkRequest = PeriodicWorkRequestBuilder<MarkAttendanceWorker>(24,TimeUnit.HOURS)
            .setInputData(inputData)
            .setInitialDelay(initialDelay,TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueue(markAttendanceWorkRequest)
        return markAttendanceWorkRequest.id
    }
}