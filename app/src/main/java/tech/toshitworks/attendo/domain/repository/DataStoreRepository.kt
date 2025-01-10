package tech.toshitworks.attendo.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveScreenSelection(screenSelection: Int)

    suspend fun readScreenSelection(): Int

    suspend fun saveMarkAttendance(markAttendance: Boolean)

    suspend fun readMarkAttendance(): Boolean

    suspend fun saveNotificationTime(time: Long)

    fun readNotificationTime(): Flow<Long>

    suspend fun saveDoMarkAttendance(doMarkAttendance: Boolean)

    fun readDoMarkAttendance(): Flow<Boolean>

    suspend fun saveDoMarkAttendanceUUID(uuid: String)

    suspend fun readDoMarkAttendanceUUID(): String

    suspend fun saveThemeState(themeState: Int)

    fun readThemeState(): Flow<Int>


}