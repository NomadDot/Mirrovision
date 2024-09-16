package com.drowsynomad.mirrovision.presentation.core.components.models

/**
 * @author Roman Voloshyn (Created on 16.09.2024)
 */

sealed class CalendarMode {
    data class Weekly(val days: List<DayCell>): CalendarMode()
    data class Monthly(val days: List<DayCell>): CalendarMode()
}