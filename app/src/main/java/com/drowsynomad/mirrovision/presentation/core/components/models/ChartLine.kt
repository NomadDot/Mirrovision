package com.drowsynomad.mirrovision.presentation.core.components.models

import androidx.annotation.StringRes
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment.*
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties

/**
 * @author Roman Voloshyn (Created on 20.09.2024)
 */

class AnimatedLine {
    companion object {
        fun create(
            color: CategoryMainColor,
            values: List<Double>
        ): List<Line> {
            return listOf(
                Line(
                    label = "",
                    values = values,
                    popupProperties = PopupProperties(enabled = false),
                    color = SolidColor(color.pureColor),
                    firstGradientFillColor = color.pureColor.copy(alpha = .7f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 4.dp,
                        color = SolidColor(color.accent.pureColor),
                    )
                )
            )
        }
    }
}

class AxisXLabels {
    companion object {
        fun create(segment: StatisticSegment): List<Int> {
            return when(segment) {
                WEEK -> listOf(R.array.weekly_days)
                MONTH -> List(31) { it + 1 }
                YEAR -> listOf(R.array.monthly_days)
            }
        }

        fun getLineCount(segment: StatisticSegment): Int {
            return when(segment) {
                WEEK -> 7
                MONTH -> 31
                YEAR -> 12
            }
        }
    }
}