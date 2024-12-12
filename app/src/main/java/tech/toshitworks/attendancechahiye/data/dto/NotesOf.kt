package tech.toshitworks.attendancechahiye.data.dto

import androidx.room.Embedded
import tech.toshitworks.attendancechahiye.data.entity.AttendanceEntity
import tech.toshitworks.attendancechahiye.data.entity.NoteEntity

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