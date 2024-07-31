package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

data class HabitRegularity(
    val id: Long = 0,
    val habitId: Long = 0,
    val time: String = emptyString(),
    val days: List<Int> = emptyList(),
    val type: RegularityType = RegularityType.WEEKLY
)