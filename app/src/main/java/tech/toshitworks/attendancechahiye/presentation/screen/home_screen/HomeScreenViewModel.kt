package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.data.repository.CsvWorkRepository
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.utils.SnackBarWorkerEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    attendanceRepository: AttendanceRepository,
    private val csvWorkRepository: CsvWorkRepository,
    private val workManager: WorkManager
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

    private val _event = MutableSharedFlow<SnackBarWorkerEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val subjectList = subjectRepository.getSubjects()
            _state.update {
                it.copy(
                    subjectList = subjectList,
                    todayDate = date,
                    todayDay = day,
                    isLoading = false
                )
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

            is HomeScreenEvents.OnExportClick -> {
                val uuid = csvWorkRepository.enqueueCsvWorker(event.tables)
                workManager.getWorkInfoByIdLiveData(uuid).observeForever { wi ->
                    when (wi?.state) {
                        WorkInfo.State.ENQUEUED -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export started..."
                                )
                            }
                        }

                        WorkInfo.State.RUNNING -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export running..."
                                )
                            }
                            viewModelScope.launch {
                                _event.emit(
                                    SnackBarWorkerEvent.ShowSnackBarForCSVWorker(
                                        "Export running..."
                                    )
                                )
                            }
                        }

                        WorkInfo.State.SUCCEEDED -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export Succeeded..."
                                )
                            }
                            viewModelScope.launch {
                                _event.emit(
                                    SnackBarWorkerEvent.ShowSnackBarForCSVWorker(
                                        "Export succeeded..."
                                    )
                                )
                            }
                        }

                        WorkInfo.State.FAILED -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export failed..."
                                )
                            }
                            println(wi.outputData.getString("error"))
                            viewModelScope.launch {
                                _event.emit(
                                    SnackBarWorkerEvent.ShowSnackBarForCSVWorker(
                                        "Export failed..."
                                    )
                                )
                            }
                        }

                        WorkInfo.State.BLOCKED -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export blocked..."
                                )
                            }
                        }

                        WorkInfo.State.CANCELLED -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export cancelled..."
                                )
                            }
                        }

                        null -> {
                            _state.update {
                                it.copy(
                                    csvWorkerState = "Export error..."
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}