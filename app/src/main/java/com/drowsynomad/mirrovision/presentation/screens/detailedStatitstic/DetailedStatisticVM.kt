package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic

import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticEvent
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

@HiltViewModel
class DetailedStatisticVM @Inject constructor(
    private val habitRepository: IHabitRepository
): StateViewModel<DetailedStatisticState, DetailedStatisticEvent, SideEffect>(
    DetailedStatisticState(isLoading = true)
) {
    override fun handleUiEvent(uiEvent: DetailedStatisticEvent) {
        when(uiEvent) {
            is DetailedStatisticEvent.LoadStatistic -> loadStatistic(uiEvent.habitId)
        }
    }

    private fun loadStatistic(habitId: Long) {
        launch {
            val statistic = habitRepository.loadHabitStatistic(habitId)
            updateState {
                it.copy(
                    habitName = statistic.name,
                    habitDescription = statistic.description,
                    color = statistic.color,
                    habitIcon = R.drawable.ic_self_education_books,
                    isLoading = false
                )
            }
        }
    }
}