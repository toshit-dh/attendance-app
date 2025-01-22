package tech.toshitworks.attendo.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tech.toshitworks.attendo.di.RepoEntryPoint
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.navigation.ScreenRoutes
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private const val TAG = "MarkAttendance"


class MarkAttendanceWorker(
    ctx: Context,
    params: WorkerParameters,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val repoEntryPoint = EntryPointAccessors.fromApplication(
                    applicationContext,
                    RepoEntryPoint::class.java
                )
                val timetableRepository = repoEntryPoint.timetableRepository()
                val attendanceRepository = repoEntryPoint.attendanceRepository()
                val subjectRepository = repoEntryPoint.subjectRepository()
                val notificationWorkRepository = repoEntryPoint.notificationWorkRepository()
                val dayRepository = repoEntryPoint.dayRepository()
                val channelData = inputData.getString("channelId")!!
                val currentDate = LocalDate.now().toString()
                val currentDay =
                    LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                val dayModel = dayRepository.getDayByName(currentDay)
                val timetable = timetableRepository.getTimetableForDay(currentDay).first()
                val output = workDataOf("success" to "no day success")
                if (timetable.isEmpty()) {
                    return@withContext Result.success(output)
                }
                var marked = false
                timetable.forEach {
                    try {
                        val editedDate = it.editedForDates?.find {date->
                            date.split(':')[0] == currentDate
                        }
                        val editedSubjectId = editedDate?.split(':')?.get(1)?.toLong() ?: -1
                        val editedSubject = subjectRepository.getSubjectById(editedSubjectId)
                        val deleted = it.deletedForDates?.contains(currentDate) == true
                        Log.e(TAG,"$deleted deleted")
                        Log.e(TAG,"$editedDate $editedSubjectId $editedSubject")
                        Log.e(TAG,"edited date is null ${editedDate == null}")
                        attendanceRepository.insertAttendance(
                            AttendanceModel(
                                day = dayModel,
                                subject = if (editedDate!= null) editedSubject else it.subject,
                                period = it.period,
                                date = currentDate,
                                isPresent = false,
                                deleted = deleted
                            )
                        )
                        marked = true
                    } catch (e: Exception) {
                        Log.e(
                            TAG,
                            "Error marking attendance for ${it.subject} - ${it.period}: ${e.message}"
                        )
                        e.printStackTrace()
                    }
                }
                if (marked)
                    notificationWorkRepository.enqueueNotificationWorker(
                        id = System.currentTimeMillis().toInt(),
                        screen = ScreenRoutes.TodayAttendance,
                        title = "Attendance Marked",
                        subText = "For $currentDay",
                        content = "Today's attendance has been marked as absent.",
                        channelId = channelData,
                        delay = 0
                    )
                val outputData = workDataOf("success" to "success ${if (marked) "marked" else "not marked"}")
                Result.success(outputData)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Error processing the work: ${e.message}", e)
                Log.e(TAG, "Error processing the work: ${e.message}")
                Result.failure()
            }
        }
    }
}