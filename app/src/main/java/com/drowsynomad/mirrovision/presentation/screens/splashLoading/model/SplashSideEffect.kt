package com.drowsynomad.mirrovision.presentation.screens.splashLoading.model

import com.drowsynomad.mirrovision.presentation.core.common.SideEffect

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

interface SplashSideEffect: SideEffect {
    fun navigateToDashboard()
    fun navigateToIntro()
}