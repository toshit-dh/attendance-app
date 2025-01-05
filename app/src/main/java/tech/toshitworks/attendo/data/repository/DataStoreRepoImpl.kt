package tech.toshitworks.attendo.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.toshitworks.attendo.data.datastore.notification_time_select
import tech.toshitworks.attendo.data.datastore.screen_selection
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
}