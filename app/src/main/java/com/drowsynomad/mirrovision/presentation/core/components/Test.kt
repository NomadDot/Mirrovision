package com.drowsynomad.mirrovision.presentation.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColorShades
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColoredCategory
import com.drowsynomad.mirrovision.presentation.navigation.TestNavigation
import com.drowsynomad.mirrovision.presentation.theme.CategoryColors
import com.drowsynomad.mirrovision.presentation.theme.GreenCategoryAccent

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
private fun ComponentsPreview(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(color = Color.Gray)
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        BigTitle(text = "Звичка", modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start)
            .padding(bottom = 24.dp)
            .padding(horizontal = 24.dp)
        )

        CategoryTitle(text = "Спорт",
            color = GreenCategoryAccent,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 15.dp),
        ) {
            InputField(modifier = Modifier.fillMaxWidth(), hint = "Hello!", maxLimit = 30)
            InputField(modifier = Modifier.fillMaxWidth(), hint = "Hello!")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 15.dp),
        ) {
            SecondaryButton(text = "Скасувати",  modifier = Modifier.weight(1f)) {}
            PrimaryButton(text = "Зберегти", modifier = Modifier.weight(1f)) {
                onClick.invoke()
            }
        }

        val colors = CategoryColors.map { color ->
            ColoredCategory(color = ColorShades(color, color.accent))
        }.dropLast(5).toMutableStateList()

        fun selectCategory(selectedOption: ColoredCategory) {
            colors.forEach { it.selected = false }
            colors.find { it.color == selectedOption.color }
                ?.selected = true
        }

      /*  ColorPicker(
            colors = colors,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) { innerColor ->
            selectCategory(innerColor)
        }*/
        AddingButton(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 23.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainTestScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Box(modifier = modifier.fillMaxSize()) {
        TestNavigation(navController)
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp),
            bottomNavigationActions = BottomNavigationActions(onHomeNavigation = { navController.popBackStack() } )
        )
    }
}

@Composable
fun ScreenA(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
       ComponentsPreview(onClick)
    }
}

@Composable
fun ScreenB(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "Second screen", modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
    }
}