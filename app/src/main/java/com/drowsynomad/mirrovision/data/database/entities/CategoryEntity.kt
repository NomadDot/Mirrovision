package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

@Entity(
    tableName = "categories"
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = emptyString(),
    val icon: Int = 0,
    val bgColor: String = emptyString()
) {
    fun toDomain(): Category =
        Category(
            id = id, name = name, icon = icon, bgColor = CategoryMainColor.parse(bgColor)
        )
}

data class CategoryUpdateEntity(
    val id: Int = 0,
    val name: String? = null,
    val icon: Int? = null,
    val bgColor: String? = null
)
