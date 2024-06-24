package com.drowsynomad.mirrovision.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.presentation.utils.composableOf
import kotlin.reflect.KClass

/**
 * @author Roman Voloshyn (Created on 19.06.2024)
 */

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<Routes.SplashScreen> = Routes.SplashScreen::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composableOf<Routes.SplashScreen> { _, _ ->

        }
    }
}