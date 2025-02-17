package tech.toshitworks.attendo.data.repository

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import tech.toshitworks.attendo.domain.repository.CsvWorkRepository
import tech.toshitworks.attendo.workers.CSVWorker
import java.util.UUID
import javax.inject.Inject

class CsvWorkRepositoryImpl @Inject constructor(
    private val workManager: WorkManager
) : CsvWorkRepository {

    override fun enqueueCsvWorker(tablesToFetch: List<String>): UUID {
        val inputData = Data.Builder()
            .putString("tableNames", tablesToFetch.joinToString(","))
            .build()

        val workRequest = OneTimeWorkRequestBuilder<CSVWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueue(workRequest)
        return workRequest.id
    }
}
