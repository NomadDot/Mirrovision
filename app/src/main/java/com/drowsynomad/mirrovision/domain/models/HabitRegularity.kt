package com.drowsynomad.mirrovision.domain.models

import android.os.Parcelable
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.data.database.entities.HabitRegularity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

data class HabitRegularity(
    val id: Long = 0,
    val habitId: Long = 0,
    val time: String = emptyString(),
    val days: List<Int> = emptyList(),
    val type: RegularityType
) {
    fun toData(): HabitRegularity {
        return HabitRegularity(
            id = id,
            habitId = habitId,
            time = time,
            days = days,
            type = type.toString()
        )
    }
}

@Serializable
@Parcelize
sealed class RegularityType: Parcelable {
    data class WeeklyType(
        val localizedDayNames: List<String> = emptyList()
    ): RegularityType()
    data object MonthlyType: RegularityType()

    companion object {
        fun toType(value: String): RegularityType = when(value) {
            "WeeklyType" -> WeeklyType()
            "MonthlyType" -> MonthlyType
            else -> WeeklyType()
        }
    }
}