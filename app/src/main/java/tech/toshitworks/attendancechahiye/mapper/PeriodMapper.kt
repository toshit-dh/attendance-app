package tech.toshitworks.attendancechahiye.mapper

import tech.toshitworks.attendancechahiye.data.entity.PeriodEntity
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel

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