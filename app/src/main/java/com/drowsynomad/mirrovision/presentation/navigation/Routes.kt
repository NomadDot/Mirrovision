package com.drowsynomad.mirrovision.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 21.06.2024)
 */

@Serializable
sealed class Routes {
    @Serializable
    data object SplashScreen: Routes()
}