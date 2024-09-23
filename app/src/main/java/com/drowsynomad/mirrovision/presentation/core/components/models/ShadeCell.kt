package com.drowsynomad.mirrovision.presentation.core.components.models

import androidx.compose.ui.graphics.Color
import com.drowsynomad.mirrovision.core.lighterColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlin.random.Random

interface ShadeCell {
    companion object {
        const val SHADE_FRACTION: Double = 0.6
    }

    val progress: CellProgress

    fun calculateShade(color: CategoryMainColor): Color {
        val lighterColor = lighterColor(color.accent.asRgb, SHADE_FRACTION)

        return when (progress) {
            CellProgress.NO_ACTIVITY -> color.pureColor
            CellProgress.NOT_FINISHED -> Color(lighterColor)
            CellProgress.FINISHED -> color.accent.pureColor
        }
    }
}

data class Cell(
    val dayId: Long,
    override val progress: CellProgress,
    val isCurrentDay: Boolean = false
): ShadeCell

data class DayCell(
    val dayId: Long = Random.nextLong(),
    override val progress: CellProgress,
    val dayPosition: String,
    val isDayInFuture: Boolean
) : ShadeCell
