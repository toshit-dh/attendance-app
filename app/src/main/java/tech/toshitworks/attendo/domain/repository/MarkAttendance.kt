package tech.toshitworks.attendo.domain.repository

import java.util.UUID

interface MarkAttendance {
    fun markAttendance(channelId: String): UUID?
}