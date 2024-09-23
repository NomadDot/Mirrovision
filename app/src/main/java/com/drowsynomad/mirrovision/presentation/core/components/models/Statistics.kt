package com.drowsynomad.mirrovision.presentation.core.components.models

import androidx.annotation.StringRes
import com.drowsynomad.mirrovision.R

/**
 * @author Roman Voloshyn (Created on 14.09.2024)
 */

enum class StatisticSegment(
    @StringRes val stringResource: Int
) {
    WEEK(R.string.statistic_segment_week),
    MONTH(R.string.statistic_segment_month),
    YEAR(R.string.statistic_segment_year);
}

enum class CellProgress {
    NO_ACTIVITY, NOT_FINISHED, FINISHED;

    companion object {
        fun calculateProgress(cellAmount: Int, filledCellAmount: Int): CellProgress {
            return when(filledCellAmount.toFloat() / cellAmount.toFloat()) {
                0f -> NO_ACTIVITY
                1f -> FINISHED
                else -> NOT_FINISHED
            }
        }
    }
}