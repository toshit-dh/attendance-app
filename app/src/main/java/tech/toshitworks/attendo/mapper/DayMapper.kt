package tech.toshitworks.attendo.mapper

import tech.toshitworks.attendo.data.entity.DayEntity
import tech.toshitworks.attendo.domain.model.DayModel

fun DayEntity.toModel(): DayModel = DayModel(
    id = this.id,
    name = this.name
)

fun DayModel.toEntity(): DayEntity = DayEntity(
    id = this.id?:0,
    name = this.name
)