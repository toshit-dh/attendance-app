package tech.toshitworks.attendo.data.dto

import androidx.room.ColumnInfo


data class AttendanceBySubject(
    @ColumnInfo(name = "subjectId") val subjectId: Long,
    @ColumnInfo(name = "subjectName") val subjectName: String,
    @ColumnInfo(name = "facultyName") val facultyName: String,
    @ColumnInfo(name = "isAttendanceCounted") val isAttendanceCounted: Boolean,
    val lecturesPresent: Int,
    val lecturesTaken: Int
)
