package tech.toshitworks.attendo.domain.model

data class NotesOf(
    val note: NoteModel,
    val attendance: Six
)
data class Six(
    val id: Long,
    val subjectId: Long,
    val periodId: Long,
    val dayId: Long,
    val date: String,
    val isPresent: Boolean
)
