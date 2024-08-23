package com.drowsynomad.mirrovision.presentation.screens.statistic

import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticState
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 21.08.2024)
 */

@HiltViewModel
class StatisticsViewModel @Inject constructor(

): StateViewModel<StatisticState, StatisticsEvent, SideEffect>(
    StatisticState()
) {
    override fun handleUiEvent(uiEvent: StatisticsEvent) {
        when(uiEvent) {
            else -> {}
        }
    }
}