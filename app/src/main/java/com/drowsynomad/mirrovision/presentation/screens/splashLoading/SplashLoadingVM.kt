package com.drowsynomad.mirrovision.presentation.screens.splashLoading

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.domain.user.IUserRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashLoadingEvent
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashLoadingState
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.model.SplashSideEffect
import com.drowsynomad.mirrovision.presentation.utils.LocaleUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

private data class UserConfiguration(
    val isFinishedPreset: Boolean,
    val language: String
)

@HiltViewModel
class SplashLoadingVM @Inject constructor(
    private val userRepository: IUserRepository
): StateViewModel<SplashLoadingState, SplashLoadingEvent, SplashSideEffect>(
    SplashLoadingState()
) {
    override fun handleUiEvent(uiEvent: SplashLoadingEvent) {
        when(uiEvent) {
            is SplashLoadingEvent.LoadUserConfiguration -> loadUserConfiguration(uiEvent.context, uiEvent.deviceLocalization)
        }
    }

    private fun loadUserConfiguration(context: Context, deviceLocalization: String) {
        viewModelScope.launch {
            delay(300)
            userRepository
                .doesUserFinishPreset()
                .zip(userRepository.getUserLanguageId(deviceLocalization)) { userFinishPreset, language ->
                    UserConfiguration(userFinishPreset, language)
                }
                .collect { userConfiguration ->
                    LocaleUtils.setLocale(context, userConfiguration.language)
                    if(userConfiguration.isFinishedPreset) sideEffect?.navigateToDashboard()
                    else sideEffect?.navigateToIntro()
                }
        }
    }
}