package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model

import androidx.annotation.DrawableRes
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Monthly
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Weekly
import com.drowsynomad.mirrovision.presentation.core.components.models.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.models.DayCell
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

data class DetailedStatisticState(
    val habitName: String = emptyString(),
    val habitDescription: String = emptyString(),
    val color: CategoryMainColor = CategoryMainColor.Purple,
    @DrawableRes val habitIcon: Int = R.drawable.ic_add,
    val isLoading: Boolean = false,

    val weeklyCalendar: Weekly = Weekly("", emptyList()),
    val monthlyCalendar: Monthly = Monthly("", emptyList()),
    val yearCalendar: CalendarMode.Yearly = CalendarMode.Yearly("", emptyList()),
    val weeklyChartValues: List<Double> = emptyList(),
    val monthlyChartValues: List<Double> = emptyList(),
    val yearChartValues: List<Double> = emptyList(),
): UiState