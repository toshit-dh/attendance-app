package tech.toshitworks.attendo.presentation.screen.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import tech.toshitworks.attendo.domain.repository.MarkAttendance
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val markAttendance: MarkAttendance,
    private val workManager: WorkManager
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenStates())
    val state = combine(
        _state,
        dataStoreRepository.readNotificationTime(),
        dataStoreRepository.readDoMarkAttendance()
    ) { state, time, doMark ->
        state.copy(
            isLoading = false,
            notificationTime = time,
            doMarkAttendance = doMark
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsScreenStates()
    )


    fun onEvent(event: SettingsScreenEvents) {
        when (event) {
            is SettingsScreenEvents.OnNotificationTimeChange -> {
                viewModelScope.launch {
                    dataStoreRepository.saveNotificationTime(event.time)
                    _state.update {
                        it.copy(
                            notificationTime = event.time
                        )
                    }
                }
            }

            is SettingsScreenEvents.OnDoMarkAttendanceChange -> {
                viewModelScope.launch {
                    dataStoreRepository.saveDoMarkAttendance(event.doMarkAttendance)
                    if (event.doMarkAttendance){
                        val uuid = markAttendance.markAttendance("mark_attendance_channel")
                        dataStoreRepository.saveDoMarkAttendanceUUID(uuid.toString())
                    }else{
                        val uuid = dataStoreRepository.readDoMarkAttendanceUUID()
                        workManager.cancelWorkById(UUID.fromString(uuid))
                        dataStoreRepository.saveDoMarkAttendanceUUID("")
                    }
                    _state.update {
                        it.copy(
                            doMarkAttendance = event.doMarkAttendance
                        )
                    }
                }
            }
        }
    }
}