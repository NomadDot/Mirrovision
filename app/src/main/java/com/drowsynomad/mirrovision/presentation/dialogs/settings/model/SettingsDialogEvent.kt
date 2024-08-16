package com.drowsynomad.mirrovision.presentation.dialogs.settings.model

import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

sealed class SettingsDialogEvent: UiEvent {
    data object LoadContent: SettingsDialogEvent()
    data class SelectLanguage(val localeId: String): SettingsDialogEvent()
}