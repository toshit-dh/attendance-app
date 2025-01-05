package tech.toshitworks.attendo.mapper

import tech.toshitworks.attendo.data.entity.NoteEntity
import tech.toshitworks.attendo.domain.model.NoteModel

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
