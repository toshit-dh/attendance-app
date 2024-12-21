package tech.toshitworks.attendancechahiye.data.converters
import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromDatesListToString(forDates: List<String>): String {
        println("List to be stored: $forDates")
        return forDates.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringDatesToList(forDates: String?): List<String> {
        println("String fetched from DB: $forDates")
        val result = forDates?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
        println("Converted List: $result")
        println(result.size)
        return result
    }
}
