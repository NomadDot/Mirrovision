package com.drowsynomad.mirrovision.core

import android.annotation.SuppressLint
import com.drowsynomad.mirrovision.presentation.core.components.models.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.models.DayCell
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

/**
 * @author Roman Voloshyn (Created on 21.09.2024)
 */

private const val datePattern = "yyyy-MM-dd"
private const val weeklyPattern = "MM.dd"

private val today by lazy { LocalDate() }

val dayFormat by lazy { SimpleDateFormat("dd") }

@SuppressLint("SimpleDateFormat")
fun generateDayId(): Long {
    val dateFormat = SimpleDateFormat(datePattern)
    val currentDate = dateFormat.format(Date())
    return DateTime(currentDate).millis.removeTimePart()
}

enum class CalendarLoading {
    PREVIOUS, CURRENT, NEXT
}

private fun generateCustomWeekId(
    startWith: Int = DateTimeConstants.MONDAY,
    count: Int = 0,
    calendarLoading: CalendarLoading = CalendarLoading.CURRENT
): Long {
    val customDay = when(calendarLoading) {
        CalendarLoading.PREVIOUS -> today.minusWeeks(count)
        CalendarLoading.CURRENT -> today
        CalendarLoading.NEXT -> today.plusWeeks(count)
    }

    return customDay.withDayOfWeek(startWith)
        .toDate().time.milliseconds
        .toLong(DurationUnit.MILLISECONDS)
        .removeTimePart()
}

@SuppressLint("SimpleDateFormat")
fun generateCurrentWeekId(startWith: Int = DateTimeConstants.MONDAY): Long {
    return generateCustomWeekId(startWith)
}

fun generateCalendarWeekId(
    startWith: Int = DateTimeConstants.MONDAY,
    count: Int,
    calendarLoading: CalendarLoading
) = generateCustomWeekId(startWith, count, calendarLoading)

data class DatePeriod(
    val start: Long,
    val end: Long
)

fun generateCalendarWeekPeriodId(
    count: Int,
    calendarLoading: CalendarLoading
): DatePeriod {
    val start = generateCustomWeekId(DateTimeConstants.MONDAY, count, calendarLoading)
    val end = generateCustomWeekId(DateTimeConstants.SUNDAY, count, calendarLoading)
    return DatePeriod(start, end)
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
fun getWeekSegmentTitle(
    startWith: Long
): String {
    val now = LocalDate(startWith.withTimePart())
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

internal fun Long.removeTimePart(): Long = this.toString().dropLast(5).toLong()
internal fun Long.withTimePart(): Long = this * 100000

internal val currentDay by lazy {
    val dateFormat = SimpleDateFormat(datePattern)
    val currentDate = dateFormat.format(Date())
    DateTime(currentDate)
}

fun Long.toDay(): String {
    val date = Date(this)
    return dayFormat.format(date)
}

fun generateWeekDays(
    currentWeekId: Long = generateCurrentWeekId()
): List<DayCell> {
    val output = mutableListOf<DayCell>()

    val startOfWeek = DateTime(currentWeekId.withTimePart())

    repeat(7) {
        val dayId = startOfWeek.plusDays(it)
            .toDate().time.milliseconds
            .toLong(DurationUnit.MILLISECONDS)

        output.add(
            DayCell(
                dayId = dayId.removeTimePart(),
                progress = CellProgress.NO_ACTIVITY,
                dayPosition = dayId.toDay(),
                isDayInFuture = false
            )
        )
    }

    return output
}