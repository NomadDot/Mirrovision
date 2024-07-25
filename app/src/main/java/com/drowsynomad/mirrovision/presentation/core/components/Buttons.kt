package com.drowsynomad.mirrovision.presentation.core.components

import android.annotation.SuppressLint
import android.widget.Toolbar
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColorShades
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColoredCategory
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary
import com.drowsynomad.mirrovision.presentation.theme.LightTextInactive
import com.drowsynomad.mirrovision.presentation.theme.ShadowColor
import com.drowsynomad.mirrovision.presentation.theme.convertToAccentCategoryColor
import com.drowsynomad.mirrovision.presentation.theme.convertToMainCategoryColor
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.gradient
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

@Composable
fun PrimaryButton(
    text: String = emptyString(),
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    @DrawableRes icon: Int? = null,
    containerColor: Color = LightPrimary,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = { onClick?.invoke() } ,
        modifier = modifier
            .bounceClick(enableBound = isEnabled)
            .height(40.dp),
        enabled = isEnabled,
        colors = ButtonColors(containerColor = containerColor, contentColor = Color.White,
            disabledContentColor = LightTextInactive, disabledContainerColor = containerColor.copy(alpha = 0.4f))
    ) {
        if(icon == null)
            Text(text = text, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        else
            Icon(painter = painterResource(id = icon), contentDescription = emptyString())
    }
}

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    val isCustomContainerColor = containerColor != LightPrimary
  /*  val buttonModifier = modifier.bounceClick()
    if(isCustomContainerColor)
        buttonModifier.border(width = 2.dp, color = containerColor, shape = RoundedCornerShape(25.dp))
    else
        buttonModifier.gradientStroke(GradientMain)
    */
    Button(
        onClick = onClick::invoke,
        modifier = modifier
            .bounceClick()
            .border(width = 2.dp, color = containerColor, shape = RoundedCornerShape(25.dp))
            .height(40.dp),
        colors = ButtonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if(isCustomContainerColor) containerColor else MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ConfigurationButton(
    text: String = emptyString(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    @DrawableRes icon: Int,
    containerColor: Color,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .bounceClick(enableBound = isEnabled) {  onClick?.invoke() }
            .background(color = containerColor, shape = RoundedCornerShape(25.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.wrapContentSize())
        Icon(
            painter = painterResource(id = icon),
            contentDescription = emptyString(),
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun HomeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_home),
        contentDescription = emptyString(),
        modifier = modifier
            .size(50.dp)
            .bounceClick {
                onClick.invoke()
            }
            .gradient(GradientMain)
            .padding(12.dp),
        tint = Color.White
    )
}

@Composable
fun AddingButton(
    modifier: Modifier = Modifier,
    color: Color? = null,
    elevation: Dp = 20.dp,
    onClick: (() -> Unit)? = null
){
    Box(
        modifier = modifier
            .bounceClick { onClick?.invoke() }
            .height(56.dp)
            .shadow(elevation, shape = RoundedCornerShape(25.dp), spotColor = ShadowColor)
            .roundBox(color = Color.White),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = emptyString(),
            modifier = Modifier.align(Alignment.Center),
            tint = color ?: MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    color: Color? = null,
    onClick: (() -> Unit)? = null
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_back_arrow),
        contentDescription = null,
        modifier = modifier.clickable { onClick?.invoke() },
        tint = color ?: MaterialTheme.colorScheme.secondary)
}

@Composable
fun CancelableAndSaveableButton(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    @StringRes cancelButtonLabel: Int,
    @StringRes primaryButtonLabel: Int,
    isPrimaryButtonEnabled: Boolean = true,
    onCancelButtonClick: (() -> Unit)? = null,
    onPrimaryButtonClick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SecondaryButton(
            text = stringResource(id = cancelButtonLabel),
            modifier = Modifier.weight(1f),
            containerColor = containerColor
        ) {
            onCancelButtonClick?.invoke()
        }
        PrimaryButton(
            text = stringResource(id = primaryButtonLabel),
            modifier = Modifier.weight(1f),
            isEnabled = isPrimaryButtonEnabled,
            containerColor = containerColor
        ) {
            onPrimaryButtonClick?.invoke()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Buttons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        PrimaryButton(text = "Зберегти", modifier = Modifier.fillMaxWidth(), isEnabled = false) {}
        SecondaryButton(text = "Скасувати", modifier = Modifier.fillMaxWidth()) {}
        HomeButton {}
        AddingButton(modifier = Modifier.fillMaxWidth())
        CancelableAndSaveableButton(
            containerColor = LightPrimary,
            cancelButtonLabel = R.string.button_cancel,
            primaryButtonLabel = R.string.button_save,
            isPrimaryButtonEnabled = false
        )
    }
}