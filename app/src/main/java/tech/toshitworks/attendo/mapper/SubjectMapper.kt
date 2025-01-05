package tech.toshitworks.attendo.mapper

import tech.toshitworks.attendo.data.entity.SubjectEntity
import tech.toshitworks.attendo.domain.model.SubjectModel

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