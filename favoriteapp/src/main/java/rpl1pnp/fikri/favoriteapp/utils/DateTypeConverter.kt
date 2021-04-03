package rpl1pnp.fikri.favoriteapp.utils

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dataToTimestamp(date: Date?): Long? {
        return date?.time
    }
}