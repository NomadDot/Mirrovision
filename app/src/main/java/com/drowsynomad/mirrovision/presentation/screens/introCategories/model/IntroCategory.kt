package com.drowsynomad.mirrovision.presentation.screens.introCategories.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

data class IntroCategory(
    @StringRes val name: Int = 0,
    @DrawableRes val icon: Int = 0
)
