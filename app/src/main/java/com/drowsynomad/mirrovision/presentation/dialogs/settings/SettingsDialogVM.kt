package com.drowsynomad.mirrovision.presentation.dialogs.settings

import androidx.compose.runtime.toMutableStateList
import com.drowsynomad.mirrovision.domain.language.ILanguageRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.dialogs.settings.model.SettingsDialogEvent
import com.drowsynomad.mirrovision.presentation.dialogs.settings.model.SettingsDialogState
import com.drowsynomad.mirrovision.presentation.utils.IStringConverterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

@HiltViewModel
class SettingsDialogVM @Inject constructor(
    private val stringManager: IStringConverterManager,
    private val languageRepository: ILanguageRepository
): StateViewModel<SettingsDialogState, SettingsDialogEvent, SideEffect>(
    SettingsDialogState()
) {
    override fun handleUiEvent(uiEvent: SettingsDialogEvent) {
        when(uiEvent) {
            SettingsDialogEvent.LoadContent -> loadContent()
            is SettingsDialogEvent.SelectLanguage -> selectLanguage(uiEvent.localeId)
        }
    }

    private fun loadContent() {
        launch {
            languageRepository.getSupportedLanguagesWithUserSelected()
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
                        it.copy(languageContent = language.toMutableStateList())
                    }
                }
        }
    }

    private fun selectLanguage(id: String) {
        uiState.value.languageContent.find { it.isSelected.value }?.isSelected?.value = false
        uiState.value.languageContent.find { it.id == id }
            ?.isSelected?.value = true
    }
}