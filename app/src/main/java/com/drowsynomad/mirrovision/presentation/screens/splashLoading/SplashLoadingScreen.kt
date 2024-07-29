package com.drowsynomad.mirrovision.presentation.screens.splashLoading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.getLocale
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashLoadingEvent
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashLoadingState
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashSideEffect

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

private fun attachSideEffects(
    onNewUserNavigation: Navigation,
    onExistedUserNavigation: Navigation
) = object: SplashSideEffect {
    override fun navigateToIntro() = onNewUserNavigation.invoke()
    override fun navigateToDashboard() = onExistedUserNavigation.invoke()
}

@Composable
fun SplashLoadingScreen (
    viewModel: SplashLoadingVM,
    onNewUserNavigation: Navigation,
    onExistedUserNavigation: Navigation
) {
    val context = LocalContext.current
    val deviceLanguage = getLocale().language

    StateContent(
        viewModel = viewModel,
        launchedEffect = {
            viewModel.handleUiEvent(
                SplashLoadingEvent.LoadUserConfiguration(context, deviceLanguage)
            )
        },
        sideEffect = attachSideEffects(onNewUserNavigation, onExistedUserNavigation)
    ) {
        SplashLoadingContent(it)
    }
}

@Composable
private fun SplashLoadingContent(state: SplashLoadingState) {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            modifier = Modifier
                .align(Center)
                .size(300.dp),
            painter = painterResource(id = R.drawable.ic_dzerkalo),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}