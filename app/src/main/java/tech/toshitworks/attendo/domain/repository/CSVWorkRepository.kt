package tech.toshitworks.attendo.domain.repository

import java.util.UUID

interface CsvWorkRepository {
    fun enqueueCsvWorker(tablesToFetch: List<String>): UUID
}
