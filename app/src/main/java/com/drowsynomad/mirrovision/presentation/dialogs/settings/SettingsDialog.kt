package com.drowsynomad.mirrovision.presentation.dialogs.settings

import android.view.Gravity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.BigTitle
import com.drowsynomad.mirrovision.presentation.core.components.ExpandableButton
import com.drowsynomad.mirrovision.presentation.core.components.GradientButton
import com.drowsynomad.mirrovision.presentation.dialogs.settings.model.SettingsDialogEvent
import com.drowsynomad.mirrovision.presentation.utils.AnimatedScene
import com.drowsynomad.mirrovision.presentation.utils.LocaleUtils
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Roman Voloshyn (Created on 26.07.2024)
 */


@Composable
fun SettingsDialog(
    modifier: Modifier = Modifier,
    viewModel: SettingsDialogVM = hiltViewModel(),
    onChangeDialogVisibility: () -> Unit
) {
    val animateTrigger = remember { mutableStateOf(false) }
    val languageTriggered = remember {
        mutableStateOf(false)
    }

    val isLanguageExpanded = rememberSaveable {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onChangeDialogVisibility,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        LaunchedEffect(key1 = Unit) {
            dialogWindowProvider.window.let { window ->
                window.setDimAmount(0f)
                window.setWindowAnimations(-1)
            }
            dialogWindowProvider.window.setGravity(Gravity.TOP)
            launch {
                delay(10)
                animateTrigger.value = true
            }
        }

        val context = LocalContext.current

        StateContent(
            viewModel = viewModel,
            contentBackground = Color.Transparent,
            launchedEffect = { viewModel.handleUiEvent(SettingsDialogEvent.LoadContent) }
        ) {
            key(languageTriggered.value) {
                AnimatedScene(isExpanded = animateTrigger.value) {
                    Column(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .roundBox(color = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = emptyString(),
                                tint = Color.Transparent
                            )
                            BigTitle(stringResource(id = R.string.label_settings))
                            Icon(
                                modifier = Modifier.clickable { onChangeDialogVisibility() },
                                painter = painterResource(id = R.drawable.ic_cancel),
                                contentDescription = emptyString(),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column {
                            ExpandableButton(
                                modifier = Modifier.fillMaxWidth(),
                                isExpanded = isLanguageExpanded.value,
                                title = stringResource(R.string.label_language),
                                icon = R.drawable.ic_languages,
                                expandableContent = it.languageContent,
                                onButtonClick = { isLanguageExpanded.value = !isLanguageExpanded.value }
                            ) {
                                viewModel.handleUiEvent(SettingsDialogEvent.SelectLanguage(it.id))
                                LocaleUtils.setLocale(context, it.id)
                                languageTriggered.value = !languageTriggered.value
                            }
                            ExpandableButton(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp),
                                title = stringResource(R.string.label_theme),
                                icon = R.drawable.ic_theme,
                                expandableContent = SnapshotStateList()
                            ) {
                                viewModel.handleUiEvent(SettingsDialogEvent.UpdateTheme)
                            }
                            GradientButton(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                text = stringResource(R.string.label_upgrade_to_pro),
                                icon = R.drawable.ic_pro_version
                            ) {
                                // TODO: Navigate to premium screen
                            }
                        }
                    }
                }
            }
        }
    }
}