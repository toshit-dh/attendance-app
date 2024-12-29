package tech.toshitworks.attendancechahiye.presentation.screen.events_screen

import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

data class EventScreenStates(
    val isLoading: Boolean = true,
    val isUpcomingEvents: Boolean = true,
    val subjectList: List<SubjectModel> = emptyList(),
    val endDate: String? = null,
    val eventList: List<EventModel> = emptyList(),
    val activeEvents: List<EventModel> = emptyList(),
    val pastEvents: List<EventModel> = emptyList(),
    val upcomingEvents: List<EventModel> = emptyList()
)
