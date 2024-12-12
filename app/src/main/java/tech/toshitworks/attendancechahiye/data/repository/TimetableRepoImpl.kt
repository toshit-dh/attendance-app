package tech.toshitworks.attendancechahiye.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.toshitworks.attendancechahiye.data.entity.TimetableEntity
import tech.toshitworks.attendancechahiye.data.local.TimetableDao
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
import tech.toshitworks.attendancechahiye.mapper.toModel
import javax.inject.Inject

class TimetableRepoImpl @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository,
    private val timetableDao: TimetableDao
): TimetableRepository {

    private var subjectCache: Map<String, Long>? = null
    private var dayCache: Map<String, Long>? = null
    private var periodCache: Map<String, Long>? = null

    private suspend fun getSubjectIdByName(subjectName: String): Long {
        subjectCache?.let {
            return it[subjectName] ?: fetchAndCacheSubjectId(subjectName)
        } ?: run {
            return fetchAndCacheSubjectId(subjectName)
        }
    }

    private suspend fun fetchAndCacheSubjectId(subjectName: String): Long {
        val subjectId = subjectRepository.getSubjectByName(subjectName)?.id
        if (subjectId != null) {
            subjectCache = subjectCache?.plus(subjectName to subjectId) ?: mapOf(subjectName to subjectId)
            return subjectId
        } else {
            throw IllegalArgumentException("Subject not found")
        }
    }

    private suspend fun getDayIdByName(dayName: String): Long {
        dayCache?.let {
            return it[dayName] ?: fetchAndCacheDayId(dayName)
        } ?: run {
            return fetchAndCacheDayId(dayName)
        }
    }

    private suspend fun fetchAndCacheDayId(dayName: String): Long {
        val dayId = dayRepository.getDayByName(dayName)?.id?:0
            dayCache = dayCache?.plus(dayName to dayId) ?: mapOf(dayName to dayId)
            return dayId
    }

    private suspend fun getPeriodIdByStartTime(startTime: String): Long {
        periodCache?.let {
            return it[startTime] ?: fetchAndCachePeriodId(startTime)
        } ?: run {
            return fetchAndCachePeriodId(startTime)
        }
    }

    private suspend fun fetchAndCachePeriodId(startTime: String): Long {
        val periodId = periodRepository.getPeriodByStartTime(startTime).id
            periodCache = periodCache?.plus(startTime to periodId) ?: mapOf(startTime to periodId)
            return periodId
    }

    override suspend fun insertPeriod(period: TimetableModel) {
        val subjectId = getSubjectIdByName(period.subject.name)
        val dayId = getDayIdByName(period.day.name)
        val periodId = getPeriodIdByStartTime(period.period.startTime)
        timetableDao.insertPeriod(TimetableEntity(
            subjectId = subjectId,
            dayId = dayId,
            periodId = periodId
        ))
    }

    override suspend fun insertTimetable(timetables: List<TimetableModel?>) {
        val timetableEntities = timetables.map {
            val subjectId = getSubjectIdByName(it!!.subject.name)
            val dayId = getDayIdByName(it.day.name)
            val periodId = getPeriodIdByStartTime(it.period.startTime)
            TimetableEntity(
                subjectId = subjectId,
                dayId = dayId,
                periodId = periodId
            )
        }
        timetableDao.insertTimetableForDay(timetableEntities)
    }

    override suspend fun getTimetableForDay(dayModel: DayModel): List<TimetableModel> = withContext(Dispatchers.IO) {
        val dayId = getDayIdByName(dayModel.name)
        timetableDao.getTimetableForDay(dayId).map {
            TimetableModel(
                subject = subjectRepository.getSubjectById(it.subjectId)!!,
                day = dayRepository.getDayById(it.dayId),
                period = periodRepository.getPeriodById(it.periodId)
            )
        }
    }

    override suspend fun updatePeriods(timetables: List<TimetableModel>) {
        timetableDao.updatePeriods(timetables.map {
            TimetableEntity(
                subjectId = getSubjectIdByName(it.subject.name),
                dayId = getDayIdByName(it.day.name),
                periodId = getPeriodIdByStartTime(it.period.startTime)
            )
        })
    }

    override suspend fun deletePeriod(period: TimetableModel) {
        val subjectId = getSubjectIdByName(period.subject.name)
        val dayId = getDayIdByName(period.day.name)
        val periodId = getPeriodIdByStartTime(period.period.startTime)
        timetableDao.deletePeriod(
            TimetableEntity(
                subjectId = subjectId,
                dayId = dayId,
                periodId = periodId
            )
        )
    }

    override suspend fun getPeriodsBySubject(subject: SubjectModel): List<TimetableModel> {
        val subjectId = getSubjectIdByName(subject.name)
        return timetableDao.getPeriodsBySubject(subjectId).map {
            TimetableModel(
                subject = subject,
                day = dayRepository.getDayById(it.dayId),
                period = periodRepository.getPeriodById(it.periodId)
            )
        }
    }

    override suspend fun getAllPeriods(): List<TimetableModel> {
        return timetableDao.getAllPeriods().map {
            TimetableModel(
                subject = subjectRepository.getSubjectById(it.subjectId)!!,
                day = dayRepository.getDayById(it.dayId),
                period = periodRepository.getPeriodById(it.periodId)
            )
        }
    }
}
