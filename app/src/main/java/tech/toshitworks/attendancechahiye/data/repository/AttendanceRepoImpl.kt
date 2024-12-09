package tech.toshitworks.attendancechahiye.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import tech.toshitworks.attendancechahiye.data.entity.AttendanceEntity
import tech.toshitworks.attendancechahiye.data.local.AttendanceDao
import tech.toshitworks.attendancechahiye.domain.model.AttendanceBySubject
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.AttendanceStats
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import javax.inject.Inject

class AttendanceRepoImpl @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository
) : AttendanceRepository {
    override suspend fun insertAttendance(attendance: AttendanceModel) {
        attendanceDao.insertAttendance(
            AttendanceEntity(
                id = attendance.id,
                dayId = attendance.day!!.id!!,
                subjectId = attendance.subject!!.id,
                periodId = attendance.period.id,
                date = attendance.date,
                isPresent = attendance.isPresent,
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
                isPresent = attendance.isPresent
            )
        )
    }

    override suspend fun updateAttendanceByDate(attendance: AttendanceModel) {
        attendanceDao.updateAttendanceByDate(
            date = attendance.date,
            subjectId = attendance.subject!!.id,
            periodId = attendance.period.id,
            isPresent = attendance.isPresent
        )
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