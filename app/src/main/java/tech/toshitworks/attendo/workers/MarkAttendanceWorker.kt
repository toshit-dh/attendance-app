package tech.toshitworks.attendo.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tech.toshitworks.attendo.di.RepoEntryPoint
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.TimetableRepository
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

private const val TAG = "MarkAttendance"


class MarkAttendanceWorker(
    ctx: Context,
    params: WorkerParameters,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val repoEntryPoint = EntryPointAccessors.fromApplication(applicationContext, RepoEntryPoint::class.java)
        val timetableRepository = repoEntryPoint.timetableRepository()
        val attendanceRepository = repoEntryPoint.attendanceRepository()
        val notificationWorkRepository = repoEntryPoint.notificationWorkRepository()
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val channelData = inputData.getString("channelId")!!
                val currentDate = LocalDate.now().toString()
                val currentDay = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                val timetable = timetableRepository.getTimetableForDay(currentDay).first()
                if (timetable.isEmpty()){
                    Log.d(TAG,"No timetable found for $currentDay")
                    return@withContext Result.success()
                }
                timetable.forEach {
                    try {
                        attendanceRepository.insertAttendance(
                            AttendanceModel(
                                subject = it.subject,
                                period = it.period,
                                date = currentDate,
                                isPresent = false
                            )
                        )
                        notificationWorkRepository.showNotification(
                            id = System.currentTimeMillis().toInt(),
                            title = "Absent",
                            subText = "For $currentDay",
                            message = "Marked today's attendance as absent. If this is incorrect, you can change it on the edit attendance screen.",
                            channelId = channelData
                        )
                    } catch (e: Exception) {
                        Log.e(
                            TAG,
                            "Error marking attendance for ${it.subject} - ${it.period}: ${e.message}"
                        )
                        e.printStackTrace()
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Log.e(TAG, "Error processing the work: ${e.message}")
                Result.failure()
            }
        }
    }
}