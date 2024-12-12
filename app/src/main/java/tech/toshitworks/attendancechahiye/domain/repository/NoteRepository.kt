package tech.toshitworks.attendancechahiye.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.NotesOf

interface NoteRepository {

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(noteId: Long)

    suspend fun getAllNotes(): List<NoteModel>

    fun getNoteByAttendances(attendanceId: List<Long>): Flow<List<NoteModel>>

    fun getNotesForTodayAttendance(): Flow<List<NoteModel>>

    suspend fun getNotesByAttendance(): List<NotesOf>


}