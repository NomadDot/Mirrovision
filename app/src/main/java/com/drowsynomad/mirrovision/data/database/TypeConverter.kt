package com.drowsynomad.mirrovision.data.database

import androidx.room.TypeConverter
import com.drowsynomad.mirrovision.presentation.utils.toJson
import com.google.gson.Gson

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

class TypeConverter {
    @TypeConverter
    fun fromIntegerList(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun toIntegerList(json: String): List<Int> = Gson().fromJson(json, Array<Int>::class.java).toList()
}