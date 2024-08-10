package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.data.database.entities.HabitRegularity
import com.drowsynomad.mirrovision.data.database.entities.RegularityDays
import com.drowsynomad.mirrovision.presentation.core.common.models.RegularityContentUI

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

data class HabitRegularity(
    val id: Long = 0,
    val habitId: Long = 0,
    val time: String = emptyString(),
    val useReminder: Boolean = false,
    val selectedDays: List<RegularityDay> = emptyList(),
    val type: RegularityType
) {
    fun toData(): HabitRegularity {
        return HabitRegularity(
            id = id,
            habitId = habitId,
            time = time,
            useReminder = useReminder,
            days = RegularityDays(selectedDays.map { it.toData() }),
            type = type.toString()
        )
    }

    fun toRegularityUI(
        cancellable: Boolean = false,
    ): RegularityContentUI {
        return RegularityContentUI(
            id = id,
            presetTime = time,
            useTime = useReminder,
            cancellable = cancellable,
            type = type,
            presetDays = selectedDays.map { it.dayPosition }
        )
    }
}

data class HabitRegularities(
    val regularities: List<com.drowsynomad.mirrovision.domain.models.HabitRegularity>
)

sealed class RegularityType {
    override fun toString(): String {
        return when(this) {
            MonthlyType -> "Monthly"
            is WeeklyType -> "Weekly"
        }
    }

    data class WeeklyType(
        val localizedDayNames: List<String> = emptyList()
    ): RegularityType() {
        override fun toString(): String = "Weekly"
    }
    data object MonthlyType: RegularityType() {
        override fun toString(): String = "Monthly"
    }

    fun invert(): RegularityType {
        return if(this is WeeklyType) MonthlyType else WeeklyType()
    }

    companion object {
        fun toType(value: String): RegularityType = when(value) {
            "Weekly" -> WeeklyType()
            "Monthly" -> MonthlyType
            else -> WeeklyType()
        }
    }
}