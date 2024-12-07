package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Insert
import androidx.room.Query
import tech.toshitworks.attendancechahiye.data.entity.NoteEntity

interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM note WHERE attendance_id = :attendanceId")
    suspend fun getNotesForAttendance(attendanceId: Long): NoteEntity

    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteNote(noteId: Long)

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteEntity>


}