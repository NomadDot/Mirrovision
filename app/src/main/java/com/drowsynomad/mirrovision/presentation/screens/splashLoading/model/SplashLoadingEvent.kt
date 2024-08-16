package com.drowsynomad.mirrovision.presentation.screens.splashLoading.model

import android.content.Context
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

sealed class SplashLoadingEvent: UiEvent {
    data class LoadUserConfiguration(
        val context: Context,
        val deviceLocalization: String
    ): SplashLoadingEvent()
}