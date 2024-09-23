package com.drowsynomad.mirrovision.presentation.core.components.models

/**
 * @author Roman Voloshyn (Created on 16.09.2024)
 */

sealed class CalendarMode {
    data class Weekly(val actualPeriod: String, val days: List<DayCell>): CalendarMode()
    data class Monthly(val actualPeriod: String, val days: List<DayCell>): CalendarMode()
    data class Yearly(val actualPeriod: String, val days: List<DayCell>): CalendarMode()
}