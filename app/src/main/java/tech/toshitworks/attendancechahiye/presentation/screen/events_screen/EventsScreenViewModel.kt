package tech.toshitworks.attendancechahiye.presentation.screen.events_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.DataStoreRepository
import tech.toshitworks.attendancechahiye.domain.repository.EventRepository
import tech.toshitworks.attendancechahiye.domain.repository.NotificationWorkRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val subjectRepository: SubjectRepository,
    private val eventRepository: EventRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val notificationWorkRepository: NotificationWorkRepository
): ViewModel() {
    private val today: LocalDate = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val date = today.format(formatter)
    private val todayLocal = LocalDate.parse(date)

    private val _state = MutableStateFlow(EventScreenStates()) // Ensure correct state initialization
    val state = _state.combine(
        eventRepository.getEvents()
    ) { currentState, events ->
        val sortedEvents = events.sortedBy {
            LocalDate.parse(it.date)
        }
        val activeEvents = sortedEvents.filter {
            it.date == date
        }
        val pastEvents = sortedEvents.filter {
            val dateLocal = LocalDate.parse(it.date)
            dateLocal.isBefore(todayLocal)
        }
        val futureEvents = sortedEvents.filter {
            val dateLocal = LocalDate.parse(it.date)
            dateLocal.isAfter(todayLocal)
        }
        currentState.copy(
            isLoading = false,
            eventList = events,
            activeEvents = activeEvents,
            pastEvents = pastEvents,
            upcomingEvents = futureEvents
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EventScreenStates()
    )

    init {
        viewModelScope.launch {
            val subjects = subjectRepository.getSubjects()
            val semesters = semesterRepository.getSemester()
            _state.update {
                it.copy(
                    subjectList = subjects,
                    endDate = semesters.endDate
                )
            }
        }
    }

    fun onEvent(event: EventScreenEvents) {
        when (event) {
            is EventScreenEvents.OnAddEvent -> {
                viewModelScope.launch {
                    if (event.cancel){
                        val workUuid = event.event.notificationWorkId
                        notificationWorkRepository.cancelNotificationWork(workUuid)
                        eventRepository.addEvent(event.event.copy(
                            notificationWorkId = null
                        ))
                        return@launch
                    }
                    val time = dataStoreRepository.readNotificationTime().first()
                    val eventLocal = event.event.dateLocal!!.atStartOfDay()
                    val currentTime = LocalDateTime.now()
                    val difference = Duration.between(currentTime,eventLocal)
                    val addedDifference = difference.toMillis() + time*1000
                    val uuid = notificationWorkRepository.enqueueNotificationWorker(
                        "Event Reminder",
                        event.event.content,
                        event.channelID!!,
                        addedDifference
                    )
                    val workUuid = event.event.notificationWorkId
                    notificationWorkRepository.cancelNotificationWork(workUuid)
                    eventRepository.addEvent(event.event.copy(
                        notificationWorkId = uuid
                    ))
                }
            }
            is EventScreenEvents.OnDeleteEvent -> {
                viewModelScope.launch {
                    val workUuid = event.event.notificationWorkId
                    notificationWorkRepository.cancelNotificationWork(workUuid)
                    eventRepository.deleteEvent(event.event)
                }
            }
            is EventScreenEvents.ToggleEvents -> {
                _state.update {
                    it.copy(
                        isUpcomingEvents = !it.isUpcomingEvents
                    )
                }
            }
        }
    }
}
