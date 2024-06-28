package com.drowsynomad.mirrovision.presentation.screens.introCategories.model

import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

sealed class IntroCategoriesEvent: UiEvent {
    data object LoadLocalizedCategories: IntroCategoriesEvent()
}
