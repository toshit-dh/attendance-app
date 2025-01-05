package tech.toshitworks.attendo.presentation.screen.today_attendance_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.TimetableModel
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.NoteRepository
import tech.toshitworks.attendo.domain.repository.SemesterRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import tech.toshitworks.attendo.domain.repository.TimetableRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TodayAttendanceScreenViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val attendanceRepository: AttendanceRepository,
    private val subjectRepository: SubjectRepository,
    private val dayRepository: DayRepository,
    private val noteRepository: NoteRepository,
    private val semesterRepository: SemesterRepository,
) : ViewModel() {

    private val today: LocalDate = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val date = today.format(formatter)
    private val day = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    init {
        viewModelScope.launch {
            val subjectList = subjectRepository.getSubjects()
            val dayList = dayRepository.getDays()
            val startDate = semesterRepository.getSemester().startDate
            val endDate = semesterRepository.getSemester().endDate
            _state.update {
                it.copy(
                    dayList = dayList,
                    day = dayList.find { dmn ->
                        dmn.name == day
                    },
                    date = date,
                    startDate = startDate,
                    endDate = endDate,
                    subjectList = subjectList,
                    isLoading = false
                )
            }
        }
    }

    private val _state = MutableStateFlow(TodayAttendanceScreenStates())
    private val combinedStateFlow1 = combine(
        _state,
        attendanceRepository.getOverallAttendance(),
        attendanceRepository.getAttendanceByDate(date),
        attendanceRepository.getAttendancePercentage(),
        noteRepository.getNotesForTodayAttendance()
    ) { state, attendanceBySubject, attendanceByDate, attendanceStats, notes ->
        state.copy(
            attendanceList = attendanceBySubject,
            attendanceByDate = attendanceByDate,
            attendanceStats = attendanceStats,
            notes = notes
        )
    }

    val state = combine(
        combinedStateFlow1,
        timetableRepository.getTimetableForDay(day)
    ) { state, timetableForDay ->
        state.copy(
            timetableForDay = mergeConsecutivePeriods(timetableForDay)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TodayAttendanceScreenStates()
    )


    fun onEvent(event: TodayAttendanceScreenEvents) {
        when (event) {
            is TodayAttendanceScreenEvents.OnAddAttendance -> {
                viewModelScope.launch {
                    attendanceRepository.insertAttendance(event.attendanceModel)
                }
            }

            is TodayAttendanceScreenEvents.OnUpdateAttendance -> {
                viewModelScope.launch {
                    attendanceRepository.updateAttendanceByDate(event.attendanceModel)
                }
            }

            is TodayAttendanceScreenEvents.OnUpdatePeriod -> {
                viewModelScope.launch {
                    val tm = event.timetableModel
                    timetableRepository.editPeriodForADate(
                        tm,
                        state.value.date
                    )
                    if (event.isPresent!=null)
                        attendanceRepository.updateAttendanceByDate(
                            AttendanceModel(
                                subject = tm.subject,
                                period = tm.period,
                                date = state.value.date,
                                isPresent = event.isPresent
                            )
                        )
                }
            }

            is TodayAttendanceScreenEvents.OnDeletePeriod -> {
                viewModelScope.launch {
                    timetableRepository.deletePeriodForADate(event.timetableModel,state.value.date)
                    if (event.toInsert)
                        attendanceRepository.insertAttendance(event.attendanceModel)
                    else
                        attendanceRepository.updateAttendanceByDate(event.attendanceModel)
                }
            }

            is TodayAttendanceScreenEvents.OnAddNote -> {
                viewModelScope.launch {
                    noteRepository.insertNote(event.noteModel)
                }
            }

        }
    }
}

fun mergeConsecutivePeriods(timetable: List<TimetableModel>): List<TimetableModel> {
    val mergedTimetable = mutableListOf<TimetableModel>()
    var previousPeriod: TimetableModel? = null
    for (currentPeriod in timetable) {
        if (previousPeriod != null && previousPeriod.subject == currentPeriod.subject &&
            previousPeriod.period.endTime == currentPeriod.period.startTime
        ) {
            val mergedPeriod = previousPeriod.copy(
                period = previousPeriod.period.copy(endTime = currentPeriod.period.endTime)
            )
            mergedTimetable[mergedTimetable.size - 1] = mergedPeriod
            previousPeriod = null
        } else {
            mergedTimetable.add(currentPeriod)
            previousPeriod = currentPeriod
        }
    }
    return mergedTimetable
}
