package com.drowsynomad.mirrovision.core

import android.annotation.SuppressLint
import android.graphics.Color
import com.drowsynomad.mirrovision.presentation.core.components.models.Cell
import com.drowsynomad.mirrovision.presentation.core.components.models.CellProgress
import kotlin.math.min
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

fun emptyString(): String = ""

fun Int.isNegative(): Boolean = this < 0.0f
fun Int.isZero(): Boolean = this == 0

const val CELL_AMOUNT_WITH_CURRENT_WEEK = 154

@SuppressLint("SimpleDateFormat")
fun createCells(
    selectedDaysId: HashMap<Long, CellProgress>
): List<Cell> {
    val outputData = mutableListOf<Cell>()

    val dayInWeekPosition = (7 - currentDay.dayOfWeek)
    val currentDayPosition = CELL_AMOUNT_WITH_CURRENT_WEEK - dayInWeekPosition

    val firstDay = currentDay.minusDays(currentDayPosition)

    for (dayPosition in  1 ..CELL_AMOUNT_WITH_CURRENT_WEEK) {
        var progress = CellProgress.NO_ACTIVITY
        val dayId = firstDay.plusDays(dayPosition).millis.removeTimePart()

         selectedDaysId[dayId]?.let { progress = it }

        outputData.add(
            Cell(
                dayId = dayId,
                progress = progress,
                isCurrentDay = dayPosition == currentDayPosition
            )
        )
    }

    return outputData
}

@SuppressLint("SimpleDateFormat")
fun createMockCells(
): List<Cell> {
    val outputData = mutableListOf<Cell>()

    for (dayPosition in 154 downTo 1) {
        outputData.add(
            Cell(
                dayId = Random.nextLong(),
                progress = CellProgress.entries.random()
            )
        )
    }

    return outputData
}

fun lighterColor(color: Int, fraction: Double): Int {
    var red = Color.red(color)
    var green = Color.green(color)
    var blue = Color.blue(color)
    red = lightenColor(red, fraction)
    green = lightenColor(green, fraction)
    blue = lightenColor(blue, fraction)
    val alpha = Color.alpha(color)
    return Color.argb(alpha, red, green, blue)
}

private fun lightenColor(color: Int, fraction: Double): Int {
    return min(color + (color * fraction), 255.0).toInt()
}