package tech.toshitworks.attendo.presentation.screen.events_screen

import tech.toshitworks.attendo.domain.model.EventModel

sealed class EventScreenEvents {
    data class OnAddEvent(val event: EventModel,val channelID: String? = null,val cancel: Boolean) : EventScreenEvents()

    data class OnDeleteEvent(val event: EventModel) : EventScreenEvents()

    data object ToggleEvents: EventScreenEvents()

}