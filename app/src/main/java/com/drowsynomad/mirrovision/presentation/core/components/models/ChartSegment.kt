package com.drowsynomad.mirrovision.presentation.core.components.models

import androidx.annotation.StringRes
import com.drowsynomad.mirrovision.R

/**
 * @author Roman Voloshyn (Created on 14.09.2024)
 */

enum class ChartSegment(
    @StringRes val stringResource: Int
) {
    WEEK(R.string.chart_segment_week),
    MONTH(R.string.chart_segment_month),
    YEAR(R.string.chart_segment_year);
}
