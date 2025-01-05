package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.domain.model.SemesterModel

interface SemesterRepository {

    suspend fun insertSemester(semester: SemesterModel)

    suspend fun updateSemester(semester: SemesterModel)

    suspend fun getSemester(): SemesterModel

    suspend fun deleteSemester(semNumber: Int)


}