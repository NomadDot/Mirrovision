package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model

/**
 * @author Roman Voloshyn (Created on 21.09.2024)
 */

sealed class StatisticInfo(val info: String) {
    data class CompletedStatistic(val times: String): StatisticInfo(times)
    data class PomodoroStatistic(val spentTime: String): StatisticInfo(spentTime)
}