package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model

import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

sealed class DetailedStatisticEvent : UiEvent {
    data class LoadStatistic(val habitId: Long) : DetailedStatisticEvent()
    data class LoadPreviousCalendarDays(
        val habitId: Long,
        val previousCount: Int,
        val segment: StatisticSegment,
    ) : DetailedStatisticEvent()

    data class LoadNextCalendarDays(
        val habitId: Long,
        val nextCount: Int,
        val segment: StatisticSegment,
    ) : DetailedStatisticEvent()
}