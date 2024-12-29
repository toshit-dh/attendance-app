package tech.toshitworks.attendancechahiye.presentation.screen.events_screen

import tech.toshitworks.attendancechahiye.domain.model.EventModel

sealed class EventScreenEvents {
    data class OnAddEvent(val event: EventModel,val channelID: String? = null,val cancel: Boolean) : EventScreenEvents()

    data class OnDeleteEvent(val event: EventModel) : EventScreenEvents()

    data object ToggleEvents: EventScreenEvents()

}