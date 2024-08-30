package com.drowsynomad.mirrovision.presentation.screens.statistic.model

import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.components.CategoryChartData
import com.drowsynomad.mirrovision.presentation.core.components.CategoryStatistic
import com.drowsynomad.mirrovision.presentation.core.components.HabitProgressUI

/**
 * @author Roman Voloshyn (Created on 21.08.2024)
 */

data class StatisticState(
    val weeklyProgress: Float = 0f,
    val chartData: List<CategoryChartData> = emptyList(),
    val detailedHabits: List<HabitProgressUI> = emptyList(),
    val categoryStatistic: List<CategoryStatistic> = emptyList(),
    val isLoading: Boolean = false
): UiState