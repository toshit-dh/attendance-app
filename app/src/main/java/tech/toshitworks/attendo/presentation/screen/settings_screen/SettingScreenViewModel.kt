package tech.toshitworks.attendo.presentation.screen.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenStates())
    val state = _state.combine(
        dataStoreRepository.readNotificationTime()
    ) { state, time ->
        state.copy(
            isLoading = false,
            notificationTime = time
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
        }
    }
}