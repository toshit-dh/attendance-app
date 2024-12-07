package tech.toshitworks.attendancechahiye.mapper

import tech.toshitworks.attendancechahiye.data.entity.NoteEntity
import tech.toshitworks.attendancechahiye.domain.model.NoteModel

fun NoteEntity.toModel() = NoteModel(
    id = id,
    content = content,
    attendanceId = attendanceId
)

fun NoteModel.toEntity() = NoteEntity(
    id = id,
    content = content,
    attendanceId = attendanceId
)
