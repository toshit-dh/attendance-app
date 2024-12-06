package tech.toshitworks.attendancechahiye.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import tech.toshitworks.attendancechahiye.data.entity.SemesterEntity

@Dao
interface SemesterDao {

    @Insert
    suspend fun insertSemester(semester: SemesterEntity)

    @Query("SELECT * FROM semester")
    fun getSemester(): SemesterEntity

    @Update
    suspend fun updateSemester(semester: SemesterEntity)

    @Query("DELETE FROM semester WHERE semNumber = :semNumber")
    suspend fun deleteSemester(semNumber: Int)

}


