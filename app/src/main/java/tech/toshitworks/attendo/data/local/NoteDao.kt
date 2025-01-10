package tech.toshitworks.attendo.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import tech.toshitworks.attendo.data.dto.NotesOf
import tech.toshitworks.attendo.data.entity.NoteEntity

@Dao
interface NoteDao {

    @Upsert
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM note WHERE attendance_id IN (:attendanceId)")
    fun getNotesForAttendances(attendanceId: List<Long>): Flow<List<NoteEntity>>

    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteNote(noteId: Long)

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE attendance_id = :attendanceId")
    suspend fun getNoteByAttendanceId(attendanceId:  Long): NoteEntity?


    @Query("""
        SELECT n.* FROM note AS n
        INNER JOIN attendance AS a
        ON a.date = strftime('%Y-%m-%d', 'now') AND n.attendance_id = a.id
    """)
    fun getNotesForTodayAttendance(): Flow<List<NoteEntity>>

    @Query("""
        SELECT n.id AS noteId,
        n.content,
        a.id AS attendanceId,
        a.subject_id AS subjectId,
        a.period_id AS periodId,
        a.day_id AS dayId,
        a.date,
        a.is_present AS isPresent
        FROM note AS n INNER JOIN attendance AS a
        ON n.attendance_id = a.id
    """)
    suspend fun getNotesByAttendance(): List<NotesOf>

}