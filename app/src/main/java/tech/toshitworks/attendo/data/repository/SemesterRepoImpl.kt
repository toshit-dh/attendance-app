package tech.toshitworks.attendo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.toshitworks.attendo.data.local.SemesterDao
import tech.toshitworks.attendo.domain.model.SemesterModel
import tech.toshitworks.attendo.domain.repository.SemesterRepository
import tech.toshitworks.attendo.mapper.toEntity
import tech.toshitworks.attendo.mapper.toModel
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

    override suspend fun getSemester(): SemesterModel = withContext(Dispatchers.IO) {
        semesterDao.getSemester().toModel()
    }

    override suspend fun deleteSemester(semNumber: Int) {
        semesterDao.deleteSemester(semNumber)
    }
}