package tech.toshitworks.attendancechahiye.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.toshitworks.attendancechahiye.data.local.SubjectDao
import tech.toshitworks.attendancechahiye.domain.model.DayModel
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

    override suspend fun getSubjectById(subjectId: Long): SubjectModel = withContext(Dispatchers.IO){
        subjectDao.getSubjectById(subjectId).toModel()
    }

//    override suspend fun getLecturesByDays(subjectId: Long): List<DayModel> {
//        return subjectDao.getLecturesByDays(subjectId).map {
//            it.toModel()
//        }
//    }

}