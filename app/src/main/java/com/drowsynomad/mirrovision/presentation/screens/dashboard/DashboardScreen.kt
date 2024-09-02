package com.drowsynomad.mirrovision.presentation.screens.dashboard

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.BottomNavigationActions
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.BottomNavigationBar
import com.drowsynomad.mirrovision.presentation.core.components.DefaultToolbar
import com.drowsynomad.mirrovision.presentation.dialogs.settings.SettingsDialog
import com.drowsynomad.mirrovision.presentation.navigation.DashboardNavigation
import com.drowsynomad.mirrovision.presentation.navigation.navigateToDashboard
import com.drowsynomad.mirrovision.presentation.navigation.navigateToHome
import com.drowsynomad.mirrovision.presentation.navigation.navigateToStatistics
import com.drowsynomad.mirrovision.presentation.screens.dashboard.model.DashboardState
import com.drowsynomad.mirrovision.presentation.theme.MenuAccent
import com.drowsynomad.mirrovision.presentation.utils.LocalFixedInsets
import com.drowsynomad.mirrovision.presentation.utils.defaultTween

/**
 * @author Roman Voloshyn (Created on 17.07.2024)
 */

@Composable
fun DashboardScreen(
    viewModel: DashboardVM,
    onEditHabitClick: (HabitUI) -> Unit
) {
    val useStatusBarPadding = remember {
        mutableStateOf(true)
    }

    StateContent(
        viewModel = viewModel,
        useStatusBarPadding = useStatusBarPadding.value,
    ) {
        DashboardContent(
            it,
            onEditHabitClick = onEditHabitClick,
            onUseSystemBar = { useStatusBarPadding.value = it })
    }
}

@Composable
fun DashboardContent(
    state: DashboardState,
    onEditHabitClick: (HabitUI) -> Unit,
    onUseSystemBar: (Boolean) -> Unit,
) {
    val settingsVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val title = rememberSaveable {
        mutableIntStateOf(R.string.toolbar_today_habits)
    }
    val blurPercentState by animateDpAsState(
        if (settingsVisibility.value) 15.dp else 0.dp,
        label = emptyString(),
        animationSpec = defaultTween()
    )

    val alphaPercentState by animateFloatAsState(
        if (settingsVisibility.value) 0.6f else 1f,
        label = emptyString(),
        animationSpec = defaultTween()
    )

    val navController = rememberNavController()

    if(settingsVisibility.value)
        SettingsDialog {
            settingsVisibility.value = false
            onUseSystemBar.invoke(!settingsVisibility.value)
        }

    Scaffold(
        modifier = Modifier
            .background(
                if (blurPercentState > 0.dp) MenuAccent
                else Color.Transparent
            )
            .alpha(alphaPercentState)
            .blur(blurPercentState),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            DefaultToolbar(
                modifier = Modifier.padding(top =
                    if(settingsVisibility.value) 25.dp + LocalFixedInsets.current.statusBarHeight
                    else 0.dp
                ),
                text = stringResource(id = title.value),
                onSettingsClick = {
                    settingsVisibility.value = !settingsVisibility.value
                    onUseSystemBar.invoke(!settingsVisibility.value)
                })
        },
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                bottomNavigationActions = BottomNavigationActions(
                    onHomeNavigation = {
                        if(title.intValue != R.string.toolbar_today_habits) {
                            navController.navigateToHome()
                            title.intValue = R.string.toolbar_today_habits
                        }
                    },
                    onStatisticNavigation = {
                        if(title.intValue != R.string.toolbar_statistics) {
                            navController.navigateToStatistics()
                            title.intValue = R.string.toolbar_statistics
                        }
                    }
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
        ) {
            DashboardNavigation(navController, onHabitEditClick = onEditHabitClick)
        }
    }
}