package tech.toshitworks.attendancechahiye.workers

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tech.toshitworks.attendancechahiye.di.RepoEntryPoint
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.utils.toAttendanceCSV
import tech.toshitworks.attendancechahiye.utils.toEventCSV
import tech.toshitworks.attendancechahiye.utils.toSubjectCSV
import tech.toshitworks.attendancechahiye.utils.toTimetableCSV

private const val TAG = "CSVWorker"

class CSVWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    val context = applicationContext
    private val repoEntryPoint = EntryPointAccessors.fromApplication(
        context,
        RepoEntryPoint::class.java
    )
    private val subjectRepository = repoEntryPoint.subjectRepository()
    private val timetableRepository = repoEntryPoint.timetableRepository()
    private val attendanceRepository = repoEntryPoint.attendanceRepository()
    private val notesRepository = repoEntryPoint.notesRepository()
    private val eventsRepository = repoEntryPoint.eventsRepository()
    override suspend fun doWork(): Result {
        val tableNamesString = inputData.getString("tableNames")
        if (tableNamesString.isNullOrEmpty()) {
            return Result.failure()
        }
        val tableNames = tableNamesString.split(",")
        return withContext(Dispatchers.IO) {
            try {
                val subjects = if (tableNames.contains("Subjects")) async { fetchSubjectTable() }else null
                val timetable =
                    if (tableNames.contains("Timetable")) async { fetchTimetableTable()} else null
                val attendance =
                    if (tableNames.contains("Attendance")) async { fetchAttendanceTable() }else null
                val notes = if (tableNames.contains("Notes")) async { fetchNotesTable()}else null
                val events = if (tableNames.contains("Events")) async {  fetchEventsTable() }else null
                val isSuccess = createCsvFile(context,subjects?.await(), timetable?.await(), attendance?.await(), notes?.await(), events?.await())
                val workData = workDataOf("success" to isSuccess.first, *isSuccess.second.map { "exception" to it }.toTypedArray())
                Result.success(workData)
            } catch (e: Exception) {
                Log.e(TAG,e.message?:"")
                Result.failure(workDataOf("success" to false,"error" to e.message))
            }
        }

    }

    private suspend fun fetchSubjectTable(): List<SubjectModel> = withContext(Dispatchers.IO) {
        subjectRepository.getSubjects().filter {
            it.name != "Lunch" && it.name!="No Period"
        }
    }

    private suspend fun fetchTimetableTable(): List<TimetableModel> = withContext(Dispatchers.IO) {
        timetableRepository.getAllPeriods()
    }

    private suspend fun fetchAttendanceTable(): List<AttendanceModel> =
        withContext(Dispatchers.IO) {
            attendanceRepository.getAllAttendance().first()
        }

    private suspend fun fetchNotesTable(): List<NoteModel> = withContext(Dispatchers.IO) {
        notesRepository.getAllNotes()
    }

    private suspend fun fetchEventsTable(): List<EventModel> = withContext(Dispatchers.IO) {
        eventsRepository.getEvents().first()
    }

    private fun createCsvFile(
        context: Context,
        subjectData: List<SubjectModel>?,
        timetableData: List<TimetableModel>?,
        attendanceData: List<AttendanceModel>?,
        notesData: List<NoteModel>?,
        eventsData: List<EventModel>?
    ): Pair<Boolean, List<String>> {
        var success = true
        val exceptions = mutableListOf<String>()
        val contentResolver = context.contentResolver
        val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        subjectData?.let {
            try {
                val subjectUri = saveCsvToDownloads(contentResolver, downloadUri, "subjects.csv", it.toSubjectCSV())
                Log.d(TAG, "Saved subjects.csv to $subjectUri")
            } catch (e: Exception) {
                success = false
                exceptions.add(e.message ?: "Unknown error")
                Log.e(TAG, e.message ?: "")
            }
        }
        timetableData?.let {
            try {
                val timetableUri = saveCsvToDownloads(contentResolver, downloadUri, "timetable.csv", it.toTimetableCSV())
                Log.d(TAG, "Saved timetable.csv to $timetableUri")
            } catch (e: Exception) {
                success = false
                exceptions.add(e.message ?: "Unknown error")
                Log.e(TAG, e.message ?: "")
            }
        }
        eventsData?.let {
            try {
                val eventsUri = saveCsvToDownloads(contentResolver, downloadUri, "events.csv", it.toEventCSV())
                Log.d(TAG, "Saved events.csv to $eventsUri")
            } catch (e: Exception) {
                success = false
                exceptions.add(e.message ?: "Unknown error")
                Log.e(TAG, e.message ?: "")
            }
        }
        attendanceData?.let {
            try {
                val attendanceUri = saveCsvToDownloads(contentResolver, downloadUri, "attendance.csv", it.toAttendanceCSV(notesData))
                Log.d(TAG, "Saved attendance.csv to $attendanceUri")
            } catch (e: Exception) {
                success = false
                exceptions.add(e.message ?: "Unknown error")
                Log.e(TAG, e.message ?: "")
            }
        }
        return Pair(success, exceptions)
    }


    private fun saveCsvToDownloads(
        contentResolver: ContentResolver,
        downloadUri: Uri,
        fileName: String,
        csvData: String
    ): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val existingUri = getExistingFileUri(contentResolver, downloadUri, fileName)
        existingUri?.let {
            contentResolver.delete(it, null, null)
            Log.d(TAG, "Deleted existing file: $fileName")
        }
        val uri = contentResolver.insert(downloadUri, contentValues)
        uri?.let { contentResolver.openOutputStream(it)?.use { outputStream ->
            outputStream.write(csvData.toByteArray())
            Log.d(TAG, "Saved $fileName to $it")
            return it
        } }

        return null
    }
    private fun getExistingFileUri(
        contentResolver: ContentResolver,
        downloadUri: Uri,
        fileName: String
    ): Uri? {
        val projection = arrayOf(MediaStore.MediaColumns._ID)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val cursor = contentResolver.query(downloadUri, projection, selection, selectionArgs, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val fileId = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                return Uri.withAppendedPath(downloadUri, fileId.toString())
            }
        }
        return null
    }
}
