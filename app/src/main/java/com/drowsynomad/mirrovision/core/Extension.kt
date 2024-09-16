package com.drowsynomad.mirrovision.core

import android.annotation.SuppressLint
import android.graphics.Color
import com.drowsynomad.mirrovision.presentation.core.components.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.models.Cell
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.min
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

private const val datePattern = "yyyy-MM-dd"
private const val weeklyPattern = "MM.dd"

fun emptyString(): String = ""

fun Int.isNegative(): Boolean = this < 0.0f
fun Int.isZero(): Boolean = this == 0

@SuppressLint("SimpleDateFormat")
fun generateDayId(): Long {
    val dateFormat = SimpleDateFormat(datePattern)
    val currentDate = dateFormat.format(Date())
    return DateTime(currentDate).millis.removeTimePart()
}

@SuppressLint("SimpleDateFormat")
fun generateStartOfAWeekId(): Long {
    val today = LocalDate()
    return today.withDayOfWeek(DateTimeConstants.MONDAY)
        .toDate().time.milliseconds
        .toLong(DurationUnit.MILLISECONDS)
        .removeTimePart()
}

@SuppressLint("SimpleDateFormat")
fun generateHabitPeriodId(): Long {
    val today = LocalDate()
    return today.minusDays(CELL_AMOUNT_WITH_CURRENT_WEEK)
        .toDate().time.milliseconds
        .toLong(DurationUnit.MILLISECONDS)
        .removeTimePart()
}

@SuppressLint("SimpleDateFormat")
fun getWeekSegmentTitle(): String {
    val now = LocalDate.now()
    val dateFormat = SimpleDateFormat(weeklyPattern)

    val firstDayOfWeek = dateFormat.format(
        now.withDayOfWeek(DateTimeConstants.MONDAY).toDate()
    )
    val lastDayOfWeek = dateFormat.format(
        now.withDayOfWeek(DateTimeConstants.SUNDAY).toDate()
    )

    return "$firstDayOfWeek - $lastDayOfWeek"
}

fun getWeekSegmentLabels(): List<Int> {
    val result = mutableListOf<Int>()
    val now = LocalDate.now()

    val firstDayOfWeek = now.withDayOfWeek(DateTimeConstants.MONDAY)

    repeat(6) {
        firstDayOfWeek.plusDays(it)
        result.add(firstDayOfWeek.dayOfMonth)
    }
    return result
}

fun getMonthSegmentTitle(): String {
    return LocalDate.now().monthOfYear().asText
}

fun getYearSegmentTitle(): String {
    return LocalDate.now().year.toString()
}


private fun Long.removeTimePart(): Long = this.toString().dropLast(5).toLong()
private val currentDay by lazy {
    val dateFormat = SimpleDateFormat(datePattern)
    val currentDate = dateFormat.format(Date())
    DateTime(currentDate)
}

private const val CELL_AMOUNT_WITH_CURRENT_WEEK = 154

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