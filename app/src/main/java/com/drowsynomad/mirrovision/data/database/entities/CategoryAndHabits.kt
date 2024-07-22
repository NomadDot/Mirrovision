package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

data class CategoryAndHabits(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val habits: List<HabitEntity>
) {

}