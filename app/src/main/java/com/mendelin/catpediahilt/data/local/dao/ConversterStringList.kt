package com.mendelin.catpediahilt.data.local.dao

import androidx.room.TypeConverter

class ConversterStringList {
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toList(list: String): List<String> =
        list.split(",")
            .map { it }
}