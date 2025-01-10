package tech.toshitworks.attendo.presentation.screen.settings_screen

sealed class SettingsScreenEvents {

    data class OnNotificationTimeChange(val time: Long): SettingsScreenEvents()

    data class OnDoMarkAttendanceChange(val doMarkAttendance: Boolean): SettingsScreenEvents()

    data class OnThemeChange(val themeState: Int): SettingsScreenEvents()

}