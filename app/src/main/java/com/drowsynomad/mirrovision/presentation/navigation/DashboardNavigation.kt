package com.drowsynomad.mirrovision.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.navigation.Routes.DetailedStatisticScreen
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.DetailedStatisticScreen
import com.drowsynomad.mirrovision.presentation.screens.home.HomeScreen
import com.drowsynomad.mirrovision.presentation.screens.statistic.StatisticsScreen
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.utils.composableOf
import kotlin.reflect.KClass

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

@Composable
fun DashboardNavigation(
    navController: NavHostController,
    startDestination: KClass<Routes.HomeScreen> = Routes.HomeScreen::class,
    onHabitEditClick: (HabitUI) -> Unit,
    onChangeAppBarColor: (CategoryAccentColor) -> Unit,
    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.HomeScreen> { _ ->
            HomeScreen(
                viewModel = hiltViewModel(),
                onEditHabitClick = onHabitEditClick
            )
        }

        composable<Routes.StatisticScreen> {
            StatisticsScreen(
                viewModel = hiltViewModel(),
                onDetailedStatisticNavigation = navController::navigateToDetailedStatistic
            )
        }

        composableOf<DetailedStatisticScreen, LongParcel> { args, _ ->
            DetailedStatisticScreen(
                viewModel = hiltViewModel(),
                onChangeAppBarColor = onChangeAppBarColor,
                habitId = args.habitInt.long
            )
        }
    }
}