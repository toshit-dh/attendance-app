package tech.toshitworks.attendancechahiye.mapper

import tech.toshitworks.attendancechahiye.data.entity.SubjectEntity
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

fun SubjectEntity.toModel(): SubjectModel = SubjectModel(
    id = this.id,
    name = name,
    facultyName = facultyName,
    isAttendanceCounted = isAttendanceCounted
)

fun SubjectModel.toEntity(): SubjectEntity = SubjectEntity(
    id = this.id,
    name = name,
    facultyName = facultyName,
    isAttendanceCounted = isAttendanceCounted
)