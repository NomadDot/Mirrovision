package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model

import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

sealed class DetailedStatisticEvent: UiEvent {
    data class LoadStatistic(val habitId: Long): DetailedStatisticEvent()
}