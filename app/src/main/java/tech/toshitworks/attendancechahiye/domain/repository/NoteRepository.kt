package tech.toshitworks.attendancechahiye.domain.repository

import tech.toshitworks.attendancechahiye.domain.model.NoteModel

interface NoteRepository {

    suspend fun insertNote(note: NoteModel)

    suspend fun getNoteForAttendance(attendanceId: Long): NoteModel

    suspend fun deleteNote(noteId: Long)

    suspend fun getAllNotes(): List<NoteModel>

}