package tech.toshitworks.attendancechahiye.data.repository

import java.util.UUID

interface CsvWorkRepository {
    fun enqueueCsvWorker(tablesToFetch: List<String>): UUID
}
