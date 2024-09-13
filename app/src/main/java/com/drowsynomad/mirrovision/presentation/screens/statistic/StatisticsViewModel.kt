package com.drowsynomad.mirrovision.presentation.screens.statistic

import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.core.createCells
import com.drowsynomad.mirrovision.core.generateHabitPeriodId
import com.drowsynomad.mirrovision.core.generateStartOfAWeekId
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRecordingRepository
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.components.CategoryChartData
import com.drowsynomad.mirrovision.presentation.core.components.CategoryStatistic
import com.drowsynomad.mirrovision.presentation.core.components.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.CellProgress.Companion.calculateProgress
import com.drowsynomad.mirrovision.presentation.core.components.Filling
import com.drowsynomad.mirrovision.presentation.core.components.HabitProgressUI
import com.drowsynomad.mirrovision.presentation.core.components.HabitStatistic
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitWithRecordingUI
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticState
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 21.08.2024)
 */

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val habitRepository: IHabitRecordingRepository,
    private val categoryRepository: ICategoryRepository
): StateViewModel<StatisticState, StatisticsEvent, SideEffect>(
    StatisticState(isLoading = true)
) {
    override fun handleUiEvent(uiEvent: StatisticsEvent) {
        when(uiEvent) {
            StatisticsEvent.CreateStatistics -> configureStatistics()
        }
    }

    private val startOfWeekId by lazy { generateStartOfAWeekId() }

    private fun configureStatistics() {
        launch {
            val habitsWithRecordings = habitRepository
                .loadPeriodRecordings(startOfWeekId)

            val categories = categoryRepository.loadCategories()
            val detailedHabits = configureDetailedData().await()

            updateState {
                it.copy(
                    weeklyProgress = calculateWeeklyProgress(habitsWithRecordings),
                    chartData = calculateWeeklyChartData(categories, habitsWithRecordings),
                    categoryStatistic = calculateCategoryStatistics(categories, habitsWithRecordings),
                    detailedHabits = detailedHabits,
                    isLoading = false
                )
            }
        }
    }

    private fun calculateWeeklyProgress(habitWithRecordingUI: List<HabitWithRecordingUI>): Float {
        var weeklyPercent = 0.0f
        habitWithRecordingUI.map {
            weeklyPercent += it.calculateCompletionPercentage
        }
        weeklyPercent /= habitWithRecordingUI.size
        return weeklyPercent
    }

    private fun calculateWeeklyChartData(
        categories: List<Category>,
        habitWithRecordingUI: List<HabitWithRecordingUI>
    ): List<CategoryChartData> {
        return categories.map { category ->
            val percentageForCategory = habitWithRecordingUI
                .filter { it.habitUI.attachedCategoryId == category.id }
                .map { it.calculateCompletionPercentage }
                .average().toFloat()

            CategoryChartData(
                id = category.id,
                percent = percentageForCategory,
                categoryColor = category.bgColor,
                categoryIcon = category.icon
            )
        }
    }

    private fun calculateCategoryStatistics(
        categories: List<Category>,
        habitWithRecordingUI: List<HabitWithRecordingUI>
    ): List<CategoryStatistic> {
        return categories.map { category ->
            val habitStatistics = habitWithRecordingUI
                .filter { it.habitUI.attachedCategoryId == category.id }
                .map {
                    HabitStatistic(
                        icon = it.habitUI.icon,
                        percentage = it.calculateCompletionPercentage,
                        color = it.habitUI.accentColor
                    )
                }

            val categoryPercentage = habitStatistics
                .map { it.percentage }
                .average().toFloat()

            CategoryStatistic(
                habitStatistics = habitStatistics,
                color = category.bgColor,
                percentage = categoryPercentage
            )
        }
    }

    private suspend fun configureDetailedData(): Deferred<List<HabitProgressUI>> {
        return viewModelScope.async {
            habitRepository.loadPeriodRecordings(generateHabitPeriodId())
                .map {
                    val map = HashMap<Long, CellProgress>()

                    it.recordings.forEach { recording ->
                        map[recording.dayId] =
                            calculateProgress(recording.cells, recording.filledCells)
                    }

                    with(it.habitUI) {
                        HabitProgressUI(
                            habitId = id,
                            name = name,
                            description = description,
                            icon = icon,
                            color = backgroundColor,
                            filling = Filling(
                                backgroundColor,
                                createCells(map)
                            )
                        )
                    }
                }
        }
    }
}