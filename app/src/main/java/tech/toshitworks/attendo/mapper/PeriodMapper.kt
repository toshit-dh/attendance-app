package tech.toshitworks.attendo.mapper

import tech.toshitworks.attendo.data.entity.PeriodEntity
import tech.toshitworks.attendo.domain.model.PeriodModel

fun PeriodEntity.toModel(): PeriodModel = PeriodModel(
    id = this.id,
    startTime = this.startTime,
    endTime = this.endTime
)

fun PeriodModel.toEntity(): PeriodEntity = PeriodEntity(
    id = this.id,
    startTime = this.startTime,
    endTime = this.endTime
)