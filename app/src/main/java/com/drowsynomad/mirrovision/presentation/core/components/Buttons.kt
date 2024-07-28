package com.drowsynomad.mirrovision.presentation.core.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.common.models.ExpandableButtonContent
import com.drowsynomad.mirrovision.presentation.theme.GradientButtonColors
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary
import com.drowsynomad.mirrovision.presentation.theme.LightTextInactive
import com.drowsynomad.mirrovision.presentation.theme.MenuAccent
import com.drowsynomad.mirrovision.presentation.theme.ShadowColor
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
    val isCustomContainerColor = containerColor != MaterialTheme.colorScheme.primary
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
    val contentModifier = modifier
        .height(50.dp)
        .bounceClick(enableBound = isEnabled, scaledOnPressed = 0.95f) { onClick?.invoke() }

    Row(
        modifier = contentModifier
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
fun GradientButton(
    modifier: Modifier,
    text: String,
    @DrawableRes icon: Int,
    colors: List<Color> = GradientButtonColors,
    strokeWidth: Dp = 50.dp,
    glowRadius: Dp? = 4.dp,
    strokeCap: StrokeCap = StrokeCap.Round,
    gradientAnimationSpeed: Int = 8000,
    progressAnimSpec: AnimationSpec<Float> = tween(
        durationMillis = 720,
        easing = LinearOutSlowInEasing
    ),
    onClick: (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = gradientAnimationSpeed,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush: ShaderBrush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val step = 1f / colors.size  // Calculate step size
                val start = step / 2 // Define start position

                // Calculate original positions for each color
                val originalSpots = List(colors.size) { start + (step * it) }

                // Apply animation offset to each color position
                val transformedSpots = originalSpots.map { spot ->
                    val shiftedSpot = (spot + offset)
                    // Wrap around if exceeds 1
                    if (shiftedSpot > 1f) shiftedSpot - 1f else shiftedSpot
                }

                // Combine colors with their transformed positions
                val pairs = colors.zip(transformedSpots).sortedBy { it.second }

                // Margin for gradient outside the progress bar
                val margin = size.width / 2

                // Create the linear gradient shader with colors and positions
                return LinearGradientShader(
                    colors = pairs.map { it.first },
                    colorStops = pairs.map { it.second },
                    from = Offset(-margin, 0f),
                    to = Offset(size.width + margin, 0f)
                )
            }
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = progressAnimSpec,
        label = ""
    )

    Box(modifier = modifier
        .fillMaxWidth()
        .bounceClick { onClick?.invoke() }
        .padding(horizontal = 8.dp)
        .height(40.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            val width = this.size.width
            val height = this.size.height

            val paint = Paint().apply {
                isAntiAlias = true
                style = PaintingStyle.Fill
                this.strokeWidth = strokeWidth.toPx()
                this.strokeCap = strokeCap
                shader = brush.createShader(size)
            }

            glowRadius?.let { radius ->
                paint.asFrameworkPaint().apply {
                    setShadowLayer(radius.toPx(), 0f, 0f, android.graphics.Color.WHITE)
                }
            }

            if (animatedProgress > 0f) {
                drawIntoCanvas { canvas ->
                    canvas.drawLine(
                        p1 = Offset(0f, height / 2f),
                        p2 = Offset(width * animatedProgress, height / 2f),
                        paint = paint
                    )
                }
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterStart)
                .padding(start = 8.dp))
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "icon",
            tint = Color.White,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)
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
fun ExpandableButton(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int,
    isExpanded: Boolean = false,
    expandableContent: SnapshotStateList<ExpandableButtonContent>,
    onButtonClick: (() -> Unit)? = null,
    onExpandableContentClick: ((ExpandableButtonContent) -> Unit)? = null
) {
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MenuAccent,
                        shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {
                    items(expandableContent, key = { item -> item.id }) {
                        SelectionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            expandableButtonContent = it,
                            onClick = onExpandableContentClick
                        )
                    }
                }
            }
        }
        ConfigurationButton(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            icon = icon,
            containerColor = MaterialTheme.colorScheme.secondary
        ) { onButtonClick?.invoke() }
    }
}

@Composable
private fun SelectionButton(
    modifier: Modifier = Modifier,
    expandableButtonContent: ExpandableButtonContent,
    onClick: ((ExpandableButtonContent) -> Unit)? = null,
) {
    Box(modifier = modifier
        .height(40.dp)
        .bounceClick { onClick?.invoke(expandableButtonContent) }
        .background(Color.White, RoundedCornerShape(25.dp))
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = stringResource(id = expandableButtonContent.title),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterEnd),
            visible = expandableButtonContent.isSelected.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_checkmark),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(20.dp)
            )
        }
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
        ExpandableButton(
            modifier = Modifier.fillMaxWidth(), title = "Мова",
            icon = R.drawable.ic_languages,
            expandableContent = mutableListOf(
                ExpandableButtonContent(title = R.string.localization_eng, initialSelection = true),
                ExpandableButtonContent(title = R.string.localization_ua),
                ExpandableButtonContent(title = R.string.localization_eng),
                ExpandableButtonContent(title = R.string.localization_ua)
            ).toMutableStateList()
        ) {
            it.isSelected.value = !it.isSelected.value
        }
    }
}