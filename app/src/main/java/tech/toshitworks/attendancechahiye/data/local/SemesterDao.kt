package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import tech.toshitworks.attendancechahiye.data.entity.SemesterEntity

@Dao
interface SemesterDao {

    @Upsert
    suspend fun insertSemester(semester: SemesterEntity)

    @Query("SELECT * FROM semester")
    fun getSemester(): SemesterEntity

    @Update
    suspend fun updateSemester(semester: SemesterEntity)

    @Query("DELETE FROM semester WHERE semNumber = :semNumber")
    suspend fun deleteSemester(semNumber: Int)

}


