package tech.toshitworks.attendo.presentation.screen.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.MarkAttendance
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    attendanceRepository: AttendanceRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val dayRepository: DayRepository,
    markAttendance: MarkAttendance
) : ViewModel() {

    private val today: LocalDate = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val date = today.format(formatter)
    private val day = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    private val _state = MutableStateFlow(HomeScreenStates())
    val state = combine(
        _state,
        attendanceRepository.getOverallAttendance(),
        attendanceRepository.getAttendancePercentage()
    ) { state, attendanceBySubject, attendanceStats ->
        state.copy(
            attendanceBySubject = attendanceBySubject,
            attendanceStats = attendanceStats
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenStates()
    )

    init {
        viewModelScope.launch {
            val subjectList = subjectRepository.getSubjects()
            val dayList = dayRepository.getDays()
            _state.update {
                it.copy(
                    subjectList = subjectList,
                    dayList = dayList,
                    todayDate = date,
                    todayDay = day,
                    isLoading = false
                )
            }
            if (!dataStoreRepository.readMarkAttendance()) {
                dataStoreRepository.saveMarkAttendance(true)
                val uuid = markAttendance.markAttendance("mark_attendance_channel")
                dataStoreRepository.saveDoMarkAttendanceUUID(uuid.toString())
            }
        }
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            HomeScreenEvents.OnEditAttendanceCalendarClick -> {
                _state.update {
                    it.copy(
                        isEditAttendanceDatePickerOpen = !_state.value.isEditAttendanceDatePickerOpen,
                    )
                }
            }

            HomeScreenEvents.OnAddExtraAttendanceClick -> {
                _state.update {
                    it.copy(
                        isAddExtraAttendanceDialogOpen = !_state.value.isAddExtraAttendanceDialogOpen
                    )
                }
            }

            is HomeScreenEvents.OnEditAttendanceDateSelected -> {
                _state.update {
                    it.copy(
                        editAttendanceDate = event.date,
                        isEditAttendanceDatePickerOpen = false
                    )
                }
            }

            HomeScreenEvents.OnSearchSubject -> {
                _state.update {
                    it.copy(
                        isSubjectSearchOpen = !_state.value.isSubjectSearchOpen
                    )
                }
            }

            is HomeScreenEvents.OnSubjectSelectForAnalysis -> {
                _state.update {
                    it.copy(
                        analysisSubject = event.subjectModel,
                        isSubjectSearchOpen = false
                    )
                }
            }

            is HomeScreenEvents.OnEditTypeChange -> {
                if (event.change == 2) {
                    _state.update {
                        it.copy(editInfo = 2)
                    }
                    return
                }
                _state.update {
                    val current = _state.value.editInfo
                    val ifNext = event.change == 1
                    it.copy(
                        editInfo = if (ifNext) (current + 1) % 3 else (current - 1 + 3) % 3
                    )
                }
            }

            HomeScreenEvents.OnNoteFilterRowVisibilityChange -> {
                _state.update {
                    it.copy(
                        isFilterRowVisible = !_state.value.isFilterRowVisible
                    )
                }
            }
        }
    }
}