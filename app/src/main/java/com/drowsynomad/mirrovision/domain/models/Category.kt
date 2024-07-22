package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.data.database.entities.CategoryEntity
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

data class Category(
    val id: Int = Random.nextInt(),
    val name: String = emptyString(),
    val icon: Int = 0,
    val bgColor: CategoryMainColor = CategoryMainColor.Purple
) {
    fun toCategoryEntity(): CategoryEntity =
        CategoryEntity(
            id = id,
            name = name,
            icon = icon,
            bgColor = bgColor.toString()
        )
}