package tech.toshitworks.attendancechahiye.domain.repository

import tech.toshitworks.attendancechahiye.domain.model.SemesterModel

interface SemesterRepository {

    suspend fun insertSemester(semester: SemesterModel)

    suspend fun updateSemester(semester: SemesterModel)

    suspend fun getSemester(): SemesterModel

    suspend fun deleteSemester(semNumber: Int)


}