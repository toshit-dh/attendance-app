package tech.toshitworks.attendo.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val screen_selection = intPreferencesKey("screen_selection")
val is_mark_attendance = booleanPreferencesKey("mark_attendance")
val notification_time_select = longPreferencesKey("notification_time_select")
val do_mark_attendance = booleanPreferencesKey("do_mark_attendance")
val do_mark_attendance_uuid = stringPreferencesKey("do_mark_attendance_uuid")