package tech.toshitworks.attendo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.toshitworks.attendo.data.local.NoteDao
import tech.toshitworks.attendo.domain.model.NoteModel
import tech.toshitworks.attendo.domain.model.NotesOf
import tech.toshitworks.attendo.domain.model.Six
import tech.toshitworks.attendo.domain.repository.NoteRepository
import tech.toshitworks.attendo.mapper.toEntity
import tech.toshitworks.attendo.mapper.toModel
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun insertNote(note: NoteModel) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(noteId: Long) {
        noteDao.deleteNote(noteId)
    }

    override suspend fun getAllNotes(): List<NoteModel> {
        return noteDao.getAllNotes().map {
            it.toModel()
        }
    }

    override fun getNoteByAttendances(attendanceId: List<Long>): Flow<List<NoteModel>> {
        return noteDao.getNotesForAttendances(attendanceId).map {
            it.map {ne->
                ne.toModel()
            }
        }
    }

    override fun getNotesForTodayAttendance(): Flow<List<NoteModel>> {
        return noteDao.getNotesForTodayAttendance().map {
            it.map { ne->
                ne.toModel()
            }
        }
    }

    override suspend fun getNotesByAttendance(): List<NotesOf> {
        return noteDao.getNotesByAttendance().map {
            NotesOf(
                note = NoteModel(
                    id = it.noteId,
                    attendanceId = it.attendanceId,
                    content = it.content
                ),
                attendance = Six(
                    id = it.attendanceId,
                    subjectId = it.subjectId,
                    periodId = it.periodId,
                    dayId = it.dayId,
                    date = it.date,
                    isPresent = it.isPresent
                )
            )
        }
    }

}
