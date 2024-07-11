package com.drowsynomad.mirrovision.presentation.screens.introCategories.model

import com.drowsynomad.mirrovision.domain.models.StringId

/**
 * @author Roman Voloshyn (Created on 28.06.2024)
 */

enum class CategoriesId {
    CATEGORY_SPORT,
    CATEGORY_DIET,
    CATEGORY_SELF_EDUCATION,
    CATEGORY_ART,
    CATEGORY_MEDITATION,
    CATEGORY_WORK,
    CATEGORY_RELATIONSHIPS,
    CATEGORY_NONE;

    companion object {
        fun toEnum(stringId: StringId): CategoriesId {
            return when (stringId.id) {
                "CATEGORY_SPORT" -> CATEGORY_SPORT
                "CATEGORY_DIET" -> CATEGORY_DIET
                "CATEGORY_SELF_EDUCATION" -> CATEGORY_SELF_EDUCATION
                "CATEGORY_ART" -> CATEGORY_ART
                "CATEGORY_MEDITATION" -> CATEGORY_MEDITATION
                "CATEGORY_WORK" -> CATEGORY_WORK
                "CATEGORY_RELATIONSHIPS" -> CATEGORY_RELATIONSHIPS
                else -> CATEGORY_NONE
            }
        }
    }
}