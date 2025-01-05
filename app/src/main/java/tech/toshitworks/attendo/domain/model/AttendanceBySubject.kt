package tech.toshitworks.attendo.domain.model

data class AttendanceBySubject(
    val subjectModel: SubjectModel,
    val lecturesPresent: Int,
    val lecturesTaken: Int
)
