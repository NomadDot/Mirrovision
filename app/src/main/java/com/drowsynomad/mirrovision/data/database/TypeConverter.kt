package com.drowsynomad.mirrovision.data.database

import androidx.room.TypeConverter
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.data.database.entities.RegularityDay
import com.google.gson.Gson

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

class TypeConverter {
    @TypeConverter
    fun fromIntegerList(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun toIntegerList(json: String): List<Int> = Gson().fromJson(json, Array<Int>::class.java).toList()

    @TypeConverter
    fun fromDaysList(list: List<RegularityDay>): String = Gson().toJson(list)

    @TypeConverter
    fun toDaysList(json: String): List<RegularityDay> = Gson().fromJson(json, Array<RegularityDay>::class.java).toList()

    @TypeConverter
    fun fromRecordingList(list: List<HabitRecord>): String = Gson().toJson(list)

    @TypeConverter
    fun toRecordingList(json: String): List<HabitRecord> = Gson().fromJson(json, Array<HabitRecord>::class.java).toList()
}