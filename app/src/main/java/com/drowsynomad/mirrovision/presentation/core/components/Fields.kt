package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.ShadowColor
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 26.06.2024)
 */

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    hint: String = emptyString(),
    maxLimit: Int? = null,
    color: Color? = null,
    elevation: Dp = 0.dp,
    isSingleLine: Boolean = false,
    prefilledValue: String = emptyString(),
    onValueChanged: ((String) -> Unit)? = null,
) {
    var textState by remember { mutableStateOf(TextFieldValue(prefilledValue)) }

    val textColor = color ?: MaterialTheme.colorScheme.secondary

    BasicTextField(
        value = textState,
        onValueChange = {
            if(maxLimit != null) {
                if(it.text.length <= maxLimit) {
                    textState = it
                    onValueChanged?.invoke(it.text)
                }
            } else {
                textState = it
                onValueChanged?.invoke(it.text)
            }
        },
        singleLine = isSingleLine,
        modifier = modifier
            .shadow(elevation, shape = RoundedCornerShape(25.dp), spotColor = ShadowColor)
            .roundBox(MaterialTheme.colorScheme.primaryContainer),
        textStyle = MaterialTheme.typography.bodyMedium.copy(textColor),
        cursorBrush = SolidColor(value = textColor),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    ) { innerTextField ->
        Box(modifier = Modifier) {
            if(textState.text.isEmpty())
                Text(text = hint,
                    style = MaterialTheme.typography.labelSmall,
                    color = color ?: MaterialTheme.colorScheme.tertiary
                )

            maxLimit?.let {
                if(textState.text.isNotEmpty())
                    Text(
                        text = "${textState.text.length}/$maxLimit",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterEnd),
                        color = color ?: MaterialTheme.colorScheme.tertiary
                    )
            }

            Box(
                modifier =
                if(maxLimit != null) Modifier.padding(end = 55.dp)
                else Modifier
            ) {
                innerTextField()
            }
        }
    }
}

@Preview
@Composable
private fun InputFieldPreview() {
    InputField(maxLimit = 12, hint = "Введіть назву звички!", modifier = Modifier.fillMaxWidth())
}