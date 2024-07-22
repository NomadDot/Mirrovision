package com.drowsynomad.mirrovision.presentation.screens.splashLoading

import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.domain.user.IUserRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashLoadingEvent
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashLoadingState
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

@HiltViewModel
class SplashLoadingVM @Inject constructor(
    private val userRepository: IUserRepository
): StateViewModel<SplashLoadingState, SplashLoadingEvent, SplashSideEffect>(
    SplashLoadingState()
) {
    override fun handleUiEvent(uiEvent: SplashLoadingEvent) {
        when(uiEvent) {
            SplashLoadingEvent.LoadUserConfiguration -> loadUserConfiguration()
        }
    }

    private fun loadUserConfiguration() {
        viewModelScope.launch {
            userRepository.doesUserFinishPreset()
                .collect { isFinishedPreset ->
                    if(isFinishedPreset) sideEffect?.navigateToDashboard()
                    else sideEffect?.navigateToIntro()
                }
        }
    }
}