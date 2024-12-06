package tech.toshitworks.attendancechahiye.data.repository

import tech.toshitworks.attendancechahiye.data.local.SemesterDao
import tech.toshitworks.attendancechahiye.domain.model.SemesterModel
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.mapper.toEntity
import tech.toshitworks.attendancechahiye.mapper.toModel
import javax.inject.Inject

class SemesterRepoImpl @Inject constructor(
    private val semesterDao: SemesterDao
): SemesterRepository {
    override suspend fun insertSemester(semester: SemesterModel) {
        semesterDao.insertSemester(semester.toEntity())
    }

    override suspend fun updateSemester(semester: SemesterModel) {
        semesterDao.updateSemester(semester.toEntity())
    }

    override suspend fun getSemester(): SemesterModel {
        return semesterDao.getSemester().toModel()
    }

    override suspend fun deleteSemester(semNumber: Int) {
        semesterDao.deleteSemester(semNumber)
    }
}