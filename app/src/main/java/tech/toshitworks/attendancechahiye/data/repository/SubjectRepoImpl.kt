package tech.toshitworks.attendancechahiye.data.repository

import tech.toshitworks.attendancechahiye.data.local.SubjectDao
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.mapper.toEntity
import tech.toshitworks.attendancechahiye.mapper.toModel
import javax.inject.Inject

class SubjectRepoImpl @Inject constructor(
    private val subjectDao: SubjectDao
): SubjectRepository{
    override suspend fun insertSubject(subject: SubjectModel) {
        subjectDao.insertSubject(subject.toEntity())
    }

    override suspend fun insertSubjects(subjects: List<SubjectModel>) {
        subjectDao.insertSubjects(subjects.map {
            it.toEntity()
        })
    }

    override suspend fun updateSubject(subject: SubjectModel) {
        subjectDao.updateSubject(subject.toEntity())
    }

    override suspend fun getSubjectByName(name: String): SubjectModel? {
        return subjectDao.getSubjectByName(name)?.toModel()
    }

    override suspend fun getSubjects(): List<SubjectModel> {
        return subjectDao.getAllSubjects().map {
            it.toModel()
        }
    }

    override fun getSubjectById(subjectId: Long): SubjectModel? {
        return subjectDao.getSubjectById(subjectId).toModel()
    }

}