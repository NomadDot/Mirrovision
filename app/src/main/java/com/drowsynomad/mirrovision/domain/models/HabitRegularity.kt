package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.data.database.entities.HabitRegularity
import com.drowsynomad.mirrovision.data.database.entities.RegularityDays

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

data class HabitRegularity(
    val id: Long = 0,
    val habitId: Long = 0,
    val time: String = emptyString(),
    val days: List<RegularityDay> = emptyList(),
    val type: RegularityType
) {
    fun toData(): HabitRegularity {
        return HabitRegularity(
            id = id,
            habitId = habitId,
            time = time,
            days = RegularityDays(days.map { it.toData() }),
            type = type.toString()
        )
    }
}

sealed class RegularityType {
    override fun toString(): String {
        return when(this) {
            MonthlyType -> "WeeklyType"
            is WeeklyType -> "MonthlyType"
        }
    }

    data class WeeklyType(
        val localizedDayNames: List<String> = emptyList()
    ): RegularityType()
    data object MonthlyType: RegularityType()

    fun invert(): RegularityType {
        return if(this is WeeklyType) MonthlyType else WeeklyType()
    }

    companion object {
        fun toType(value: String): RegularityType = when(value) {
            "WeeklyType" -> WeeklyType()
            "MonthlyType" -> MonthlyType
            else -> WeeklyType()
        }
    }
}