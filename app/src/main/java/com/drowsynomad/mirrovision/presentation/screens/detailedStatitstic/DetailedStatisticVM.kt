package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic

import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.CalendarLoading
import com.drowsynomad.mirrovision.core.generateCalendarWeekPeriodId
import com.drowsynomad.mirrovision.core.generateCurrentWeekId
import com.drowsynomad.mirrovision.core.generateWeekDays
import com.drowsynomad.mirrovision.core.getWeekSegmentTitle
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.domain.habit.IHabitRecordingRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Weekly
import com.drowsynomad.mirrovision.presentation.core.components.models.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.models.DayCell
import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticEvent
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.joda.time.DateTimeConstants
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

@HiltViewModel
class DetailedStatisticVM @Inject constructor(
    private val habitRepository: IHabitRepository,
    private val recordingRepository: IHabitRecordingRepository,
) : StateViewModel<DetailedStatisticState, DetailedStatisticEvent, SideEffect>(
    DetailedStatisticState(isLoading = true)
) {
    override fun handleUiEvent(uiEvent: DetailedStatisticEvent) {
        when (uiEvent) {
            is DetailedStatisticEvent.LoadStatistic -> loadStatistic(uiEvent.habitId)
            is DetailedStatisticEvent.LoadNextCalendarDays -> loadCalendarDays(
                uiEvent.habitId, uiEvent.segment, uiEvent.nextCount, CalendarLoading.NEXT
            )

            is DetailedStatisticEvent.LoadPreviousCalendarDays -> loadCalendarDays(
                uiEvent.habitId, uiEvent.segment, uiEvent.previousCount, CalendarLoading.PREVIOUS
            )
        }
    }

    private val currentWeekId by lazy { generateCurrentWeekId() }
    private var lastRecord = 0L

    private fun loadStatistic(habitId: Long) {
        launch(Dispatchers.IO) {
            val statistic = habitRepository.loadHabitStatistic(habitId)
            val weeklyCalendar = calculateWeeklyStatistics(habitId = habitId).await()

            withContext(Dispatchers.Main.immediate) {
                updateState {
                    it.copy(
                        habitName = statistic.name,
                        habitDescription = statistic.description,
                        color = statistic.color,
                        habitIcon = R.drawable.ic_self_education_books,
                        isLoading = false,
                        weeklyCalendar = weeklyCalendar,
//                    monthlyProgressValues = monthlyProgressValues,
//                    yearProgressValues = yearProgressValues
                    )
                }
            }
        }
    }

    private fun calculateWeeklyStatistics(
        startOfPeriod: Long = currentWeekId,
        endOfPeriod: Long = generateCurrentWeekId(DateTimeConstants.SUNDAY),
        habitId: Long,
    ): Deferred<Weekly> {
        return viewModelScope.async {
            val recordingMap = mutableMapOf<Long, HabitRecord>()
            val foundRecordings = recordingRepository
                .loadPeriodRecordings(startOfPeriod, endOfPeriod, habitId)

            foundRecordings.forEach {
                recordingMap[it.date] = it
            }

            if(lastRecord == 0L)
                lastRecord = foundRecordings.last().date

            val lastRecordedDay =
                if (foundRecordings.isNotEmpty()) foundRecordings.last().date else lastRecord
            val days = generateWeekDays(startOfPeriod)

            val label = getWeekSegmentTitle(startOfPeriod)
            val payload = days.map {
                val recording = recordingMap[it.dayId]
                val cellProgress =
                    if (recording != null) {
                        CellProgress.calculateProgress(
                            recording.amount.cellAmount,
                            recording.amount.prefilledCellAmount
                        )
                    } else null

                it.copy(
                    progress = cellProgress ?: CellProgress.NO_ACTIVITY,
                    isDayInFuture = it.dayId > lastRecordedDay
                )
            }

            Weekly(label, payload)
        }
    }

    private fun loadCalendarDays(
        habitId: Long,
        segment: StatisticSegment,
        count: Int,
        calendarLoading: CalendarLoading,
    ) {
        launch(Dispatchers.IO) {
            val defferedStatistic = when (segment) {
                StatisticSegment.WEEK -> {
                    val period = generateCalendarWeekPeriodId(count, calendarLoading)
                    calculateWeeklyStatistics(period.start, period.end, habitId)
                }

                else -> {
                    val period = generateCalendarWeekPeriodId(count, calendarLoading)
                    calculateWeeklyStatistics(period.start, period.end, habitId)
                }
            }

            val payload = defferedStatistic.await()
            withContext(Dispatchers.Main.immediate) {
                updateState {
                    it.copy(
                        weeklyCalendar = payload
                    )
                }
            }
        }
    }
}