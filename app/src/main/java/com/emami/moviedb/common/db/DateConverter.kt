package com.emami.moviedb.common.db

import androidx.room.TypeConverter
import java.time.Instant

class DateConverter {

    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }
}