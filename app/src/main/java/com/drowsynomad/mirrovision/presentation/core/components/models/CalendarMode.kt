package com.drowsynomad.mirrovision.presentation.core.components.models

/**
 * @author Roman Voloshyn (Created on 16.09.2024)
 */

data class ChartProgress(
    val values: List<Int> = emptyList(),
    val maxValue: Double = 1.0
)

sealed class CalendarMode {
    data class Weekly(
        val actualPeriod: String = "",
        val days: List<DayCell> = emptyList(),
        val completedTime: Int = 0,
        val chartProgress: ChartProgress = ChartProgress()
    ) : CalendarMode()

    data class Monthly(
        val actualPeriod: String = "",
        val days: List<DayCell> = emptyList(),
        val completedTime: Int = 0,
        val chartProgress: ChartProgress = ChartProgress()
    ) : CalendarMode()

    data class Yearly(
        val actualPeriod: String = "",
        val days: List<DayCell> = emptyList(),
        val completedTime: Int = 0,
        val chartProgress: ChartProgress = ChartProgress()
    ) : CalendarMode()
}