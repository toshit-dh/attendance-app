package tech.toshitworks.attendancechahiye.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
import java.time.LocalDate

private const val TAG = "MarkAttendance"


class MarkAttendanceWorker(
    ctx: Context,
    params: WorkerParameters,
    private val attendanceRepository: AttendanceRepository,
    private val timetableRepository: TimetableRepository
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            delay(500)
            return@withContext try {
                val currentDate = LocalDate.now().toString()
                val currentDay = LocalDate.now().dayOfWeek.toString()
                val timetable = timetableRepository.getTimetableForDay(currentDay).first()
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