package tech.toshitworks.attendancechahiye.presentation.screen.settings_screen

sealed class SettingsScreenEvents {

    data class OnNotificationTimeChange(val time: Long): SettingsScreenEvents()

}