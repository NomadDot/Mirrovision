package com.drowsynomad.mirrovision.presentation.screens.introCategories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.BigTitle
import com.drowsynomad.mirrovision.presentation.core.components.PrimaryButton
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesEvent
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategory
import com.drowsynomad.mirrovision.presentation.theme.GradientAccent
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.theme.LightMainBackground
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.gradient

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Composable
fun IntroCategoriesScreen(
    viewModel: IntroCategoriesVM,
    onNavigateNext: (() -> Unit)? = null
) {
    StateContent(
        viewModel = viewModel,
        contentBackground = LightMainBackground,
        launchedEffect = {
            viewModel.handleUiEvent(IntroCategoriesEvent.LoadLocalizedCategories)
        }
    ) { inputState ->
        inputState?.let {
            if(it.isProgress)
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .align(Alignment.Center),
                    )
                }
            else
                IntroCategoriesContent(
                    it.categories,
                    onNavigateNext = onNavigateNext
                )
        }
    }
}

@Composable
fun IntroCategoriesContent(
    categories: List<IntroCategory>,
    onNavigateNext: (() -> Unit)? = null
) {
    val isNextEnabled = rememberSaveable {
        mutableStateOf(false)
    }

    val listOfSelectedCategories = remember {
        mutableListOf<IntroCategory>().toMutableStateList()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            BigTitle(text = "Оберіть категорію",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 24.dp, end = 24.dp, bottom = 25.dp)
            )
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(end = 24.dp, start = 24.dp),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                items(categories) { category ->
                    IntroCategoryItem(category, isOwnCategory = category.icon == 0) {outCategory ->
                        if(category.icon == 0) {
                           //
                        } else
                            if(listOfSelectedCategories.contains(outCategory))
                                listOfSelectedCategories.remove(outCategory)
                            else listOfSelectedCategories.add(outCategory)

                        isNextEnabled.value = listOfSelectedCategories.isNotEmpty()
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter).background(color = LightMainBackground)) {
            if (!isNextEnabled.value)
                Text(
                    text = "Оберіть хоча б одну категорію",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 76.dp)
                        .align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center
                )
            PrimaryButton(
                text = "Продовжити",
                isEnabled = isNextEnabled.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
                onClick = onNavigateNext
            )
        }
    }
}

@Composable
private fun IntroCategoryItem(
    category: IntroCategory,
    modifier: Modifier = Modifier,
    isOwnCategory: Boolean = false,
    onClick: (IntroCategory) -> Unit
) {
    val isAnimatedStroke = remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier
        .padding(top = 5.dp)
        .bounceClick(scaledOnPressed = 0.9f) {
            onClick.invoke(category)
            if(!isOwnCategory)
                isAnimatedStroke.value = !isAnimatedStroke.value
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .gradient(if (isAnimatedStroke.value) GradientAccent else GradientMain)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = category.name),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        if(!isOwnCategory)
            Icon(
                painter = painterResource(id = category.icon),
                contentDescription = emptyString(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = (-17).dp),
                tint = Color.Unspecified
            )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column {
        IntroCategoriesContent(categories = listOf(
            IntroCategory(R.string.default_category_art, R.drawable.ic_category_art),
            IntroCategory(R.string.default_category_art, R.drawable.ic_category_art),
            IntroCategory(R.string.default_category_art, R.drawable.ic_category_art),
            IntroCategory(R.string.default_category_art, R.drawable.ic_category_art),
            IntroCategory(R.string.default_category_art, R.drawable.ic_category_art),
            IntroCategory(R.string.default_category_art, R.drawable.ic_category_art)
        ))
    }
}