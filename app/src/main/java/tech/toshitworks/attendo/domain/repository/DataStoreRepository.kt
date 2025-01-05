package tech.toshitworks.attendo.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveScreenSelection(screenSelection: Int)

    suspend fun readScreenSelection(): Int

    suspend fun saveNotificationTime(time: Long)

    fun readNotificationTime(): Flow<Long>

}