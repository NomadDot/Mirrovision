package com.drowsynomad.mirrovision.presentation.core.components.models

/**
 * @author Roman Voloshyn (Created on 25.08.2024)
 */

data class RecordingUI(
    val id: Long,
    val habitId: Long,
    val cells: Int,
    val filledCells: Int,
    val dayId: Long
)

data class HabitWithRecordingUI(
    val habitUI: HabitUI,
    val recordings: List<RecordingUI>
) {
    val calculateCompletionPercentage: Float
        get() = recordings.map { it.filledCells.toFloat() / it.cells.toFloat() }
            .average()
            .toFloat()
}