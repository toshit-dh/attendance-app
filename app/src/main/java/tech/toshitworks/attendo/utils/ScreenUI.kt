package tech.toshitworks.attendo.utils

import tech.toshitworks.attendo.R

val lightPages by lazy {
    mapOf(
        "Today's Attendance" to R.drawable.light_today_attendance,
        "Analysis" to R.drawable.light_analysis,
        "Edit Attendance" to R.drawable.light_edit_attendance,
        "Notes" to R.drawable.light_notes,
        "Events" to R.drawable.light_events,
    )
}

val darkPages by lazy {
    mapOf(
        "Today's Attendance" to R.drawable.dark_today_attendance,
        "Analysis" to R.drawable.dark_analysis,
        "Edit Attendance" to R.drawable.dark_edit_attendance,
        "Notes" to R.drawable.dark_notes,
        "Events" to R.drawable.dark_events,
    )
}

val description by lazy {
    listOf(
        "You can add today's attendance here. \n" +
                "Click on the mail icon to add a note related to attendance\n" +
                "Consecutive periods will be merged\n" +
                "Make sure to add subject name in short\n" +
                "Faculty name will be shown as initials",
        "View analysis for all subjects here\n" +
                "The overall attendance don't include attendance of subjects for which attendance is not counted\n" +
                "Subjects which are not counted in attendance will have their seperate attendance",
        "Edit Attendance Here. You can add any extra periods for any subject\n" +
                "Also you can edit attendance here",
        "You can view notes here. \n" +
                "Use the filter row to add filters on notes. \n" +
                "Click the visibility icon to toggle the visibility of filter row.",
        "You can add Events here. \n" +
                "You can turn event notification on or off by clicking bell icon",
    )
}