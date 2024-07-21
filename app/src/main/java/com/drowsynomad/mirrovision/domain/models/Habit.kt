package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 17.07.2024)
 */

data class Habit(
    val id: Long = 0,
    val attachedCategoryId: Int = 0,
    val name: String = emptyString(),
    val description: String = emptyString(),
    val icon: Int = 0,
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    val cellAmount: Int = 0,
    val filledCellAmount: Int = 0,
) {
    fun toHabitEntity(): HabitEntity = let {
        return@let HabitEntity(
            id = id,
            categoryId = attachedCategoryId,
            name = name,
            description = description,
            icon = icon,
            bgColor = backgroundColor.toString(),
            amount = HabitEntity.Amount(cellAmount, filledCellAmount)
        )
    }
}