package com.test.slideshow.data.playlist.db

import androidx.room.TypeConverter

class PlaylistConverters {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }
}