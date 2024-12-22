package tech.toshitworks.attendancechahiye.data.converters
import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromDatesListToString(forDates: List<String>): String {
        return forDates.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringDatesToList(forDates: String?): List<String> {
        return forDates?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
    }
}
