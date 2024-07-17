package com.drowsynomad.mirrovision.presentation.dialogs

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.components.BigTitle
import com.drowsynomad.mirrovision.presentation.core.components.CategoryChooserIcon
import com.drowsynomad.mirrovision.presentation.core.components.InputField
import com.drowsynomad.mirrovision.presentation.core.components.PrimaryButton
import com.drowsynomad.mirrovision.presentation.core.components.SecondaryButton
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColorPicker
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColorShades
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColoredCategory
import com.drowsynomad.mirrovision.presentation.theme.convertToAccentCategoryColor
import com.drowsynomad.mirrovision.presentation.theme.convertToMainCategoryColor
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 28.06.2024)
 */

@Composable
fun CreateCategoryDialog(
    enabledColors: List<ColorShades> = emptyList(),
    @DrawableRes prefilledIcon: Int = R.drawable.ic_diet_pizza,
    isDialogVisible: (Boolean) -> Unit,
    onSave: (ColoredCategory) -> Unit
) {
    val isButtonActive = rememberSaveable {
        mutableStateOf(false)
    }

    val categoryName = remember {
        mutableStateOf("")
    }
    val categoryIcon = remember {
        mutableIntStateOf(prefilledIcon)
    }

    val defaultBgColor = MaterialTheme.colorScheme.surfaceContainer
    val selectedBgColor = remember {
        mutableStateOf(defaultBgColor)
    }

    val defaultAccentColor = MaterialTheme.colorScheme.primary
    val selectedAccentColor = remember {
        mutableStateOf(defaultAccentColor)
    }

    val currentBackgroundColor = animateColorAsState(
        selectedBgColor.value,
        animationSpec = tween(500),
        label = emptyString()
    )

    fun checkIfButtonEnabled() {
        isButtonActive.value = categoryName.value.isNotEmpty() &&
                currentBackgroundColor.value != defaultBgColor
                && categoryIcon.intValue == R.drawable.ic_diet_pizza
    }

    val accentColor = animateColorAsState(
        selectedAccentColor.value,
        animationSpec = tween(500),
        label = emptyString()
    ) {
        checkIfButtonEnabled()
    }

    val colors = remember { enabledColors.toMutableStateList() }

    fun selectCategory(colorShade: ColorShades) {
        colors.forEach { it.selected = false }
        val selectedShade = colors.find { it.main == colorShade.main }
        selectedShade?.selected = true
    }

    Dialog(
        onDismissRequest = { isDialogVisible(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (iconButton, content) = createRefs()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .roundBox(currentBackgroundColor.value)
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                BigTitle(
                    text = stringResource(R.string.label_category),
                    color = accentColor.value)
                InputField(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(),
                    isSingleLine = true,
                    color = accentColor.value,
                    hint = stringResource(R.string.label_enter_category_name),
                    maxLimit = 30
                ) {
                    categoryName.value = it
                    checkIfButtonEnabled()
                }
                BigTitle(
                    text = stringResource(R.string.label_choose_color),
                    modifier = Modifier.padding(top = 20.dp),
                    color = accentColor.value
                )
                ColorPicker(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(),
                    colors = enabledColors,
                    colorsAlignment = Alignment.Center,
                    onColorClick = {
                        selectCategory(it)
                        selectedBgColor.value = it.main.pureColor
                        selectedAccentColor.value = it.accent.pureColor
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                ) {
                    SecondaryButton(
                        text = stringResource(id = R.string.button_cancel),
                        modifier = Modifier.weight(1f),
                        containerColor = if(accentColor.value == defaultAccentColor)
                            MaterialTheme.colorScheme.primary
                        else accentColor.value
                    ) {
                        isDialogVisible.invoke(false)
                    }
                    PrimaryButton(
                        text = stringResource(id = R.string.button_save),
                        modifier = Modifier.weight(1f),
                        isEnabled = isButtonActive.value,
                        containerColor =
                            if(accentColor.value == defaultAccentColor)
                                MaterialTheme.colorScheme.primary
                            else accentColor.value
                    ) {
                        onSave.invoke(
                            ColoredCategory(
                                color = ColorShades(
                                    convertToMainCategoryColor(currentBackgroundColor.value),
                                    convertToAccentCategoryColor(accentColor.value)),
                                name = categoryName.value,
                                icon = prefilledIcon
                            )
                        )
                        isDialogVisible.invoke(false)
                    }
                }
            }
            CategoryChooserIcon(
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .constrainAs(iconButton) {
                        top.linkTo(content.top)
                        bottom.linkTo(content.top)
                        end.linkTo(content.end, margin = 12.dp)
                    },
                icon = R.drawable.ic_add,
                iconSpec = 40.dp,
                backgroundColor = accentColor.value,
                accentColor = currentBackgroundColor.value,
                iconTint = accentColor.value
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL)
@Composable
private fun Preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CreateCategoryDialog(isDialogVisible = {}) {}
    }
}