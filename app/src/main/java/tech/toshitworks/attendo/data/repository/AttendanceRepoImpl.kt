package tech.toshitworks.attendo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import tech.toshitworks.attendo.data.entity.AttendanceEntity
import tech.toshitworks.attendo.data.local.AttendanceDao
import tech.toshitworks.attendo.domain.model.AttendanceBySubject
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.PeriodRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import javax.inject.Inject

class AttendanceRepoImpl @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository
) : AttendanceRepository {
    override suspend fun insertAttendance(attendance: AttendanceModel) {
        val dayId = dayRepository.getDayByName(attendance.day!!.name).id!!
        attendanceDao.insertAttendance(
            AttendanceEntity(
                id = attendance.id,
                dayId = dayId,
                subjectId = attendance.subject!!.id,
                periodId = attendance.period.id,
                date = attendance.date,
                isPresent = attendance.isPresent,
                deleted = attendance.deleted
            )
        )
    }

    override suspend fun updateAttendance(attendance: AttendanceModel) {
        val subjectId = subjectRepository.getSubjectByName(attendance.subject!!.name)!!.id
        attendanceDao.updateAttendance(
            AttendanceEntity(
                dayId = attendance.day!!.id!!,
                subjectId = subjectId,
                periodId = attendance.period.id,
                date = attendance.date,
                isPresent = attendance.isPresent,
                deleted = attendance.deleted
            )
        )
    }

    override suspend fun updateAttendanceByDate(attendance: AttendanceModel) {
        attendanceDao.updateAttendanceByDate(
            date = attendance.date,
            subjectId = attendance.subject!!.id,
            periodId = attendance.period.id,
            isPresent = attendance.isPresent,
            deleted = attendance.deleted
        )
    }

    override fun getAllAttendance(): Flow<List<AttendanceModel>> {
        return attendanceDao.getAllAttendance().map {
            it.map {ae->
                AttendanceModel(
                    id = ae.id,
                    day = dayRepository.getDayById(ae.dayId),
                    subject = subjectRepository.getSubjectById(ae.subjectId),
                    date = ae.date,
                    isPresent = ae.isPresent,
                    period = periodRepository.getPeriodById(ae.periodId)
                )
            }
        }
    }

    override fun getPlusDeletedAttendance(): Flow<List<AttendanceModel>> {
        return attendanceDao.getPlusDeletedAttendance().map {l->
            l.map {ae->
                AttendanceModel(
                    id = ae.id,
                    day = dayRepository.getDayById(ae.dayId),
                    subject = subjectRepository.getSubjectById(ae.subjectId),
                    date = ae.date,
                    isPresent = ae.isPresent,
                    period = periodRepository.getPeriodById(ae.periodId),
                    deleted = ae.deleted
                )
            }
        }
    }

    override fun getAttendancePercentage(): Flow<AttendanceStats> {
        return attendanceDao.getAttendancePercentage().map {
            AttendanceStats(
                totalLectures = it.totalLectures,
                totalPresent = it.totalPresent,
                attendancePercentage = it.attendancePercentage
            )
        }
    }

    override fun getAttendancePercentageBySubject(): Flow<List<AttendanceBySubject>> {
        return attendanceDao.getAttendancePercentageBySubject().map {l->
            l.map {
                AttendanceBySubject(
                    subjectModel = SubjectModel(
                        it.subjectId,
                        it.subjectName,
                        it.facultyName,
                        it.isAttendanceCounted
                    ),
                    lecturesPresent = it.lecturesPresent,
                    lecturesTaken = it.lecturesTaken
                )
            }
        }
    }

    override suspend fun deleteAttendance(attendance: AttendanceModel) {
        val subjectId = subjectRepository.getSubjectByName(attendance.subject!!.name)!!.id
        attendanceDao.deleteAttendance(
            AttendanceEntity(
                dayId = attendance.day!!.id!!,
                subjectId = subjectId,
                date = attendance.date,
                periodId = attendance.period.id,
                isPresent = attendance.isPresent
            )
        )
    }

    override fun getAttendanceByDate(date: String): Flow<List<AttendanceModel>> {
        return attendanceDao.getAttendanceByDate(date).map {
            it.map { ae ->
                val subject = withContext(Dispatchers.IO){
                    subjectRepository.getSubjectById(ae.subjectId)
                }
                val period = withContext(Dispatchers.IO){
                    periodRepository.getPeriodById(ae.periodId)
                }
                AttendanceModel(
                    id = ae.id,
                    subject = subject,
                    date = ae.date,
                    isPresent = ae.isPresent,
                    period = period
                )
            }
        }
    }

    override fun getAttendanceBySubject(subjectName: String): Flow<List<AttendanceModel>> {
        return attendanceDao.getAttendanceBySubject(subjectName).map {
            it.map { ae ->
                AttendanceModel(

                    subject = null,
                    date = ae.date,
                    isPresent = ae.isPresent,
                    period = periodRepository.getPeriodById(ae.periodId)
                )
            }
        }
    }

    override fun getAttendanceByDateRange(
        startDate: String,
        endDate: String
    ): Flow<List<AttendanceModel>> {
        return attendanceDao.getAttendanceByDateRange(startDate, endDate).map {
            it.map { ae ->
                AttendanceModel(
                    subject = subjectRepository.getSubjectById(ae.subjectId),
                    date = ae.date,
                    isPresent = ae.isPresent,
                    period = periodRepository.getPeriodById(ae.periodId)
                )
            }
        }
    }

    override fun getOverallAttendance(): Flow<List<AttendanceBySubject>> {
        return attendanceDao.getOverallAttendance().map {
            it.map { abs ->
                AttendanceBySubject(
                    subjectModel = SubjectModel(
                        abs.subjectId,
                        abs.subjectName,
                        abs.facultyName,
                        abs.isAttendanceCounted
                    ),
                    lecturesPresent = abs.lecturesPresent,
                    lecturesTaken = abs.lecturesTaken
                )
            }
        }
    }

    override suspend fun deleteAllAttendance() {
        attendanceDao.deleteAllAttendance()
    }
}