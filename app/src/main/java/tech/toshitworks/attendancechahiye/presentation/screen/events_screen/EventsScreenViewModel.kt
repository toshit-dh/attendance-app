package tech.toshitworks.attendancechahiye.presentation.screen.events_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import javax.inject.Inject

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
): ViewModel() {
}