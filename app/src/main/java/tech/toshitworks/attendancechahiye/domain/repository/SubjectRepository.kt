package tech.toshitworks.attendancechahiye.domain.repository

import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

interface SubjectRepository {

    suspend fun insertSubject(subject: SubjectModel)

    suspend fun insertSubjects(subjects: List<SubjectModel>)

    suspend fun updateSubject(subject: SubjectModel)

    suspend fun getSubjectByName(name: String): SubjectModel?

    suspend fun getSubjects(): List<SubjectModel>

    fun getSubjectById(subjectId: Long): SubjectModel?

}