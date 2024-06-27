package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.gradient
import com.drowsynomad.mirrovision.presentation.utils.gradientStroke
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier) {
        Button(
            onClick = onClick::invoke,
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .bounceClick()
                .background(
                    color = LightPrimary,
                    shape = RoundedCornerShape(25.dp)
                ),
            colors = ButtonColors(containerColor = LightPrimary, contentColor = Color.White,
                disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)
        ) {
            Text(text = text)
        }
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick::invoke,
        modifier = modifier
            .height(40.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(25.dp)
            )
            .bounceClick()
            .gradientStroke(GradientMain),
        colors = ButtonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)
    ) {
        Text(text = text)
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
    onClick: (() -> Unit)? = null
){
    Box(
        modifier = modifier
            .bounceClick { onClick?.invoke() }
            .height(56.dp)
            .roundBox(color = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = emptyString(),
            modifier = Modifier.align(Alignment.Center),
            tint = MaterialTheme.colorScheme.secondary
        )
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
        PrimaryButton(text = "Зберегти", modifier = Modifier.fillMaxWidth()) {}
        SecondaryButton(text = "Скасувати", modifier = Modifier.fillMaxWidth()) {}
        HomeButton {}
        AddingButton(modifier = Modifier.fillMaxWidth())
    }
}