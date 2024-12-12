package tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.MarkAttendance
import tech.toshitworks.attendancechahiye.domain.repository.NoteRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
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
    markAttendance: MarkAttendance
) : ViewModel() {
    private val today: LocalDate = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val date = today.format(formatter)
    init {
        val today: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = today.format(formatter)
        val day = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        markAttendance.markAttendance()
        val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        viewModelScope.launch {
            val subjectList = subjectRepository.getSubjects()
            val dayList = dayRepository.getDays()
            val timetable = timetableRepository.getTimetableForDay(DayModel(name = dayOfWeek))
            _state.update {
                it.copy(
                    dayList = dayList,
                    day = dayList.find {dmn->
                        dmn.name == day
                    },
                    date = date,
                    timetableForDay = mergeConsecutivePeriods(timetable),
                    subjectList = subjectList,
                    isLoading = false
                )
            }
        }
    }

    private val _state = MutableStateFlow(TodayAttendanceScreenStates())
    val state = combine(
        _state,
        attendanceRepository.getOverallAttendance(),
        attendanceRepository.getAttendanceByDate(date),
        attendanceRepository.getAttendancePercentage(),
        noteRepository.getNotesForTodayAttendance()
    ) { state, attendanceBySubject, attendanceByDate, attendanceStats,notes ->
        state.copy(
            attendanceList = attendanceBySubject,
            attendanceByDate = attendanceByDate,
            attendanceStats = attendanceStats,
            notes = notes
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

            is TodayAttendanceScreenEvents.OnUpdateSubject -> {
                _state.update {
                    val list = it.timetableForDay
                    val mlist = list.toMutableList()
                    mlist[event.index] = event.timetableModel
                    it.copy(
                         timetableForDay = mergeConsecutivePeriods(mlist)
                    )
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
