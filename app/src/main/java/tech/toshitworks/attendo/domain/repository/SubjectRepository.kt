package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.domain.model.SubjectModel

interface SubjectRepository {

    suspend fun insertSubject(subject: SubjectModel)

    suspend fun insertSubjects(subjects: List<SubjectModel>)

    suspend fun updateSubject(subject: SubjectModel)

    suspend fun getSubjectByName(name: String): SubjectModel?

    suspend fun getSubjects(): List<SubjectModel>

    suspend fun getSubjectById(subjectId: Long): SubjectModel?

    suspend fun getDays(subjectId: Long): List<String>

}