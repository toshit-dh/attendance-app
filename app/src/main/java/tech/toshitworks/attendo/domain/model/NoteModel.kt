package tech.toshitworks.attendo.domain.model

data class NoteModel(
    val id: Long = 0,
    val content: String,
    val attendanceId: Long
)
