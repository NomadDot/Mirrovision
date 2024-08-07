package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.formatTime
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import java.util.Calendar

/**
 * @author Roman Voloshyn (Created on 04.08.2024)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    presetTime: Time? = null,
    color: CategoryMainColor,
    onConfirm: (Time) -> Unit,
    onDismiss: () -> Unit,
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = presetTime?.hour ?: currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = presetTime?.minutes ?: currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    val colors = TimePickerColors(
        clockDialColor = color.pureColor,
        selectorColor = color.accent.pureColor,
        containerColor = color.pureColor,
        periodSelectorBorderColor = color.accent.pureColor,
        clockDialSelectedContentColor = Color.White,
        clockDialUnselectedContentColor = color.accent.pureColor,
        periodSelectorSelectedContainerColor = color.accent.pureColor,
        periodSelectorUnselectedContainerColor = color.pureColor,
        periodSelectorSelectedContentColor = color.accent.pureColor,
        periodSelectorUnselectedContentColor = color.accent.pureColor,
        timeSelectorSelectedContainerColor = color.pureColor,
        timeSelectorUnselectedContainerColor = color.pureColor,
        timeSelectorSelectedContentColor = color.accent.pureColor,
        timeSelectorUnselectedContentColor = color.accent.pureColor
    )

    Box(
        modifier = modifier.roundBox(color = Color.White)
    ) {
        Icon(
            painter = painterResource(
                id = if(expanded.value) R.drawable.ic_keyboard_input else R.drawable.ic_timer
            ),
            contentDescription = "Input",
            tint = color.accent.pureColor,
            modifier = Modifier.clickable { expanded.value =!expanded.value }
        )
        Column {
            if(expanded.value)
                TimePicker(
                    state = timePickerState,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = colors,
                )
            else
                TimeInput(
                    state = timePickerState,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = colors,
                )
            CancelableAndSaveableButton(
                cancelButtonLabel = R.string.button_cancel,
                primaryButtonLabel = R.string.button_save,
                containerColor = color.accent.pureColor,
                onCancelButtonClick = onDismiss,
                onPrimaryButtonClick = {
                    onConfirm.invoke(
                        Time(timePickerState.hour, timePickerState.minute)
                    )
                }
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    presetTime: Time? = null,
    color: CategoryMainColor,
    onConfirm: (Time) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss,
        content = {
            TimeSelector(
                presetTime = presetTime,
                modifier = Modifier.padding(horizontal = 24.dp),
                color = color,
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
        }
    )
}

data class Time(
    val hour: Int,
    val minutes: Int
) {
    val formattedTime: String = "${hour}:${minutes}".formatTime()
}

@Preview
@Composable
private fun Preview() {
    TimeSelector(
        modifier = Modifier,
        color = CategoryMainColor.Green,
        onConfirm = {},
        onDismiss = {},
    )
}