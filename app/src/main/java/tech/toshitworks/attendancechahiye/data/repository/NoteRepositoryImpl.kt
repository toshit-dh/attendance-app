package tech.toshitworks.attendancechahiye.data.repository

import tech.toshitworks.attendancechahiye.data.local.NoteDao
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.repository.NoteRepository
import tech.toshitworks.attendancechahiye.mapper.toEntity
import tech.toshitworks.attendancechahiye.mapper.toModel
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun insertNote(note: NoteModel) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun getNoteForAttendance(attendanceId: Long): NoteModel {
        return noteDao.getNotesForAttendance(attendanceId).toModel()
    }

    override suspend fun deleteNote(noteId: Long) {
        noteDao.deleteNote(noteId)
    }

    override suspend fun getAllNotes(): List<NoteModel> {
        return noteDao.getAllNotes().map {
            it.toModel()
        }
    }

}
