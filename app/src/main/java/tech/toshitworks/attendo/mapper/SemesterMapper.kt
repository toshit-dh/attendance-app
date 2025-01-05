package tech.toshitworks.attendo.mapper

import tech.toshitworks.attendo.data.entity.SemesterEntity
import tech.toshitworks.attendo.domain.model.SemesterModel

fun SemesterEntity.toModel(): SemesterModel = SemesterModel(
    id = this.id,
    semNumber = this.semNumber,
    midTermDate = this.midTermDate,
    startDate = this.startDate,
    endDate = this.endDate
)

fun SemesterModel.toEntity(): SemesterEntity = SemesterEntity(
    id = this.id,
    semNumber = this.semNumber,
    midTermDate = this.midTermDate,
    startDate = this.startDate,
    endDate = this.endDate
)