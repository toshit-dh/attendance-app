package tech.toshitworks.attendancechahiye.utils

import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

operator fun String.invoke(): String {
    return this.replace("\"", "\"\"")
        .replace(",", "\\,")
        .replace("\n", "\\n")
}

fun List<SubjectModel>.toSubjectCSV(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("ID,Subject Name,Faculty Name\n") // Column headers
    for (subject in this) {
        stringBuilder.append("${subject.id},${subject.name()},${subject.facultyName()}\n")
    }
    return stringBuilder.toString()
}

fun List<TimetableModel>.toTimetableCSV(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("ID,Day,Period,Subject\n")
    for (timetable in this) {
        stringBuilder.append("${timetable.id},${timetable.day.name},${timetable.period.startTime}-${timetable.period.endTime},${timetable.subject.name()}\n")
    }
    return stringBuilder.toString()
}

fun List<EventModel>.toEventCSV(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("ID,Date,Subject,Content\n")
    for (event in this) {
        stringBuilder.append("${event.id},${event.date},${event.subjectModel.name()},${event.content()}\n")
    }
    return stringBuilder.toString()
}

fun List<AttendanceModel>.toAttendanceCSV(notes: List<NoteModel>?): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("ID,Date,Day,Period,Subject,IsPresent,Note\n")
    for (attendance in this) {
        val note = notes?.find { it.attendanceId == attendance.id }
        val periodText = if (attendance.period.startTime=="empty" && attendance.period.endTime=="empty") "" else "${attendance.period.startTime}-${attendance.period.endTime}"
        stringBuilder.append("${attendance.id},${attendance.date},${attendance.day!!.name},$periodText,${attendance.subject!!.name()},${if (attendance.isPresent) "Present" else "Absent"},${note?.content() ?: ""}\n")
    }
    return stringBuilder.toString()
}


