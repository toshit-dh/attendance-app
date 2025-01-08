package tech.toshitworks.attendo.presentation.screen.notification_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.model.NotificationModel
import tech.toshitworks.attendo.domain.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
): ViewModel() {

    private val _state = MutableStateFlow<Pair<List<NotificationModel>,Boolean>>(Pair(emptyList(),false))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            notificationRepository.getNotifications()
            _state.update {
                Pair(notificationRepository.getNotifications(),true)
            }
        }
    }

}