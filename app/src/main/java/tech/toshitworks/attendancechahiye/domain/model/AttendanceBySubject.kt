package tech.toshitworks.attendancechahiye.domain.model

data class AttendanceBySubject(
    val subjectModel: SubjectModel,
    val lecturesPresent: Int,
    val lecturesTaken: Int
)
