package tech.toshitworks.attendo.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.toshitworks.attendo.data.datastore.do_mark_attendance
import tech.toshitworks.attendo.data.datastore.do_mark_attendance_uuid
import tech.toshitworks.attendo.data.datastore.is_mark_attendance
import tech.toshitworks.attendo.data.datastore.notification_time_select
import tech.toshitworks.attendo.data.datastore.screen_selection
import tech.toshitworks.attendo.data.datastore.theme_state
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import javax.inject.Inject


class DataStoreRepoImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
): DataStoreRepository {
    override suspend fun saveScreenSelection(screenSelection: Int) {
        datastore.edit {
            it[screen_selection] = screenSelection
        }
    }

    override suspend fun readScreenSelection(): Int {
        val preferences = datastore.data.first()
        return preferences[screen_selection] ?: 0
    }

    override suspend fun saveMarkAttendance(markAttendance: Boolean) {
        datastore.edit {
            it[is_mark_attendance] = markAttendance
        }
    }

    override suspend fun readMarkAttendance(): Boolean {
        val preferences = datastore.data.first()
        return preferences[is_mark_attendance] ?: false
    }

    override suspend fun saveNotificationTime(time: Long) {
        datastore.edit {
            it[notification_time_select] = time
        }
    }

    override fun readNotificationTime(): Flow<Long> {
        val preferences = datastore.data.map{
            it[notification_time_select] ?: 0
        }
        return preferences
    }

    override suspend fun saveDoMarkAttendance(doMarkAttendance: Boolean) {
        datastore.edit {
            it[do_mark_attendance] = doMarkAttendance
        }
    }

    override fun readDoMarkAttendance(): Flow<Boolean> {
        val preferences = datastore.data.map {
            it[do_mark_attendance] ?: true
        }
        return preferences
    }

    override suspend fun saveDoMarkAttendanceUUID(uuid: String) {
        datastore.edit {
            it[do_mark_attendance_uuid] = uuid
        }
    }

    override suspend fun readDoMarkAttendanceUUID(): String {
        val preferences = datastore.data.first()
        return preferences[do_mark_attendance_uuid] ?: ""

    }

    override suspend fun saveThemeState(themeState: Int) {
        datastore.edit {
            it[theme_state] = themeState
        }
    }

    override fun readThemeState(): Flow<Int> {
        return datastore.data.map {
            it[theme_state] ?: 0
        }
    }
}