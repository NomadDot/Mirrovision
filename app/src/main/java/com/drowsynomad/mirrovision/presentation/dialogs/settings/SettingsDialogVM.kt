package com.drowsynomad.mirrovision.presentation.dialogs.settings

import androidx.compose.runtime.toMutableStateList
import com.drowsynomad.mirrovision.domain.user.IUserRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.dialogs.settings.model.SettingsDialogEvent
import com.drowsynomad.mirrovision.presentation.dialogs.settings.model.SettingsDialogState
import com.drowsynomad.mirrovision.presentation.theme.updateColorPalette
import com.drowsynomad.mirrovision.presentation.utils.IStringConverterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

@HiltViewModel
class SettingsDialogVM @Inject constructor(
    private val stringManager: IStringConverterManager,
    private val userRepository: IUserRepository
): StateViewModel<SettingsDialogState, SettingsDialogEvent, SideEffect>(
    SettingsDialogState()
) {
    override fun handleUiEvent(uiEvent: SettingsDialogEvent) {
        when(uiEvent) {
            SettingsDialogEvent.LoadContent -> loadContent()
            is SettingsDialogEvent.SelectLanguage -> selectLanguage(uiEvent.localeId)
            SettingsDialogEvent.UpdateTheme -> updateTheme()
        }
    }

    private fun updateTheme() {
        launch {
            val isDarkMode = uiState.value.darkModeEnabled.not()
            userRepository.changeUserTheme(isDarkMode)
            updateColorPalette(isDarkMode)
            updateState {
                it.copy(darkModeEnabled = isDarkMode)
            }
        }
    }

    private fun loadContent() {
        var darkModeEnabled = false
        launch {
            userRepository.getSupportedLanguagesWithUserSelected()
                .zip(userRepository.isDarkMode()) { language, isDarkMode ->
                    darkModeEnabled = isDarkMode
                    language
                }
                .map {
                    it.map {
                        it.toExpandableButtonItem(
                            stringManager.getLocalizationByKey(it.id)
                        )
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect { language ->
                    updateState {
                        it.copy(
                            languageContent = language.toMutableStateList(),
                            darkModeEnabled = darkModeEnabled
                        )
                    }
                }
        }
    }

    private fun selectLanguage(id: String) {
        uiState.value.languageContent.find { it.isSelected.value }?.isSelected?.value = false
        uiState.value.languageContent.find { it.id == id }
            ?.isSelected?.value = true

        launch {
            userRepository.saveUserLanguage(id)
        }
    }
}