package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model

import androidx.annotation.DrawableRes
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Monthly
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Weekly
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

    val weekly: Weekly = Weekly("", emptyList()),
    val monthly: Monthly = Monthly("", emptyList()),
    val year: CalendarMode.Yearly = CalendarMode.Yearly("", emptyList())
): UiState