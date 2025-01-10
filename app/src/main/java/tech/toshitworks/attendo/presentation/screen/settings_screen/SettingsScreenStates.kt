package tech.toshitworks.attendo.presentation.screen.settings_screen

data class SettingsScreenStates(
    val isLoading: Boolean = true,
    val notificationTime: Long = 0,
    val doMarkAttendance: Boolean = false,
    val themeState: Int = 0,
)