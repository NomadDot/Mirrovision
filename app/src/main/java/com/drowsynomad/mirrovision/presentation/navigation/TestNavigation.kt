package com.drowsynomad.mirrovision.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.presentation.core.components.ScreenA
import com.drowsynomad.mirrovision.presentation.core.components.ScreenB
import com.drowsynomad.mirrovision.presentation.utils.composableOf
import kotlin.reflect.KClass

/**
 * @author Roman Voloshyn (Created on 26.06.2024)
 */

@Composable
fun TestNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<Routes.MainTestScreen> = Routes.MainTestScreen::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composableOf<Routes.MainTestScreen> { _, _ ->
            ScreenA(onClick = { navController.navigate(Routes.SecondaryTestScreen)})
        }
        composableOf<Routes.SecondaryTestScreen> { _, _ ->
            ScreenB()
        }
    }
}