package tech.toshitworks.attendo.data.dto

data class NotesOf(
    val noteId: Long,
    val content: String,
    val attendanceId: Long,
    val subjectId: Long,
    val periodId: Long,
    val dayId: Long,
    val date: String,
    val isPresent: Boolean

)