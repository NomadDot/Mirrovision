package com.drowsynomad.mirrovision.presentation.screens.splashLoading

import androidx.compose.runtime.Composable
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.DefaultProgress
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
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(SplashLoadingEvent.LoadUserConfiguration) },
        sideEffect = attachSideEffects(onNewUserNavigation, onExistedUserNavigation)
    ) {
        SplashLoadingContent(it)
    }
}

@Composable
private fun SplashLoadingContent(state: SplashLoadingState) {
    DefaultProgress()
}