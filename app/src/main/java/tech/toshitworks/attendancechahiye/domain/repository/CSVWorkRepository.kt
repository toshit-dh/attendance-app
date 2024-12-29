package tech.toshitworks.attendancechahiye.domain.repository

import java.util.UUID

interface CsvWorkRepository {
    fun enqueueCsvWorker(tablesToFetch: List<String>): UUID
}
