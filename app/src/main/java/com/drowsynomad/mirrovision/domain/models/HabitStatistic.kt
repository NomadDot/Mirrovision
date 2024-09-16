package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 15.09.2024)
 */

data class HabitStatistic(
    val name: String = "",
    val description: String = "",
    val color: CategoryMainColor = CategoryMainColor.Purple,
    val icon: Int = 0,
    val completedTime: String = "0",
    val pomodoroTime: String = "00:00"
)