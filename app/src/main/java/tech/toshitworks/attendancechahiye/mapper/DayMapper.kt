package tech.toshitworks.attendancechahiye.mapper

import tech.toshitworks.attendancechahiye.data.entity.DayEntity
import tech.toshitworks.attendancechahiye.domain.model.DayModel

fun DayEntity.toModel(): DayModel = DayModel(
    id = this.id,
    name = this.name
)

fun DayModel.toEntity(): DayEntity = DayEntity(
    id = this.id?:0,
    name = this.name
)