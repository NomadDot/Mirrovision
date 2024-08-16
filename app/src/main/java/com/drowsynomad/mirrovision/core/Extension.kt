package com.drowsynomad.mirrovision.core

import android.annotation.SuppressLint
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

private const val datePattern = "yyyy-MM-dd"

fun emptyString(): String = ""

fun Int.isNegative(): Boolean = this < 0.0f
fun Int.isZero(): Boolean = this == 0

@SuppressLint("SimpleDateFormat")
fun generateDayId(): Long {
    val dateFormat = SimpleDateFormat(datePattern)
    val currentDate = dateFormat.format(Date())
    return DateTime(currentDate).millis.removeTimePart()
}

private fun Long.removeTimePart(): Long = this.toString().dropLast(5).toLong()