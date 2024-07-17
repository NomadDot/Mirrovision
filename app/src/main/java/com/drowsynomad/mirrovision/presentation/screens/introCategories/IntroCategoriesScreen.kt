package com.drowsynomad.mirrovision.presentation.screens.introCategories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.components.AdviceText
import com.drowsynomad.mirrovision.presentation.core.components.BigTitle
import com.drowsynomad.mirrovision.presentation.core.components.PrimaryButton
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColorShades
import com.drowsynomad.mirrovision.presentation.dialogs.CreateCategoryDialog
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesEvent
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesEvent.InsertCustomCategory
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesEvent.SelectCategory
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesState
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoryUI
import com.drowsynomad.mirrovision.presentation.theme.CategoryColors
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
    onNavigateNext: ((List<CategoryUI>) -> Unit)? = null
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(IntroCategoriesEvent.LoadLocalizedCategories) }
    ) { state ->
        if (state.isProgress)
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
                state,
                onCategoryClick = { viewModel.handleUiEvent(SelectCategory(it)) },
                onAddCategory = { viewModel.handleUiEvent(InsertCustomCategory(it)) },
                onNavigateNext = onNavigateNext
            )
    }
}

@Composable
fun IntroCategoriesContent(
    state: IntroCategoriesState,
    onCategoryClick: ((IntroCategoryUI) -> Unit)? = null,
    onAddCategory: ((IntroCategoryUI) -> Unit)? = null,
    onNavigateNext: ((List<CategoryUI>) -> Unit)? = null
) {
    val categories = state.categories

    val isNextEnabled = rememberSaveable { mutableStateOf(false) }
    val isDialogShown = rememberSaveable { mutableStateOf(false) }

    fun checkIfNextButtonEnabled() {
        isNextEnabled.value = categories.any { it.selection.value }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(isDialogShown.value)
            CreateCategoryDialog(
                enabledColors = CategoryColors.map { color -> ColorShades(color, color.accent) },
                isDialogVisible = { isDialogShown.value = it },
                onSave = {
                    onAddCategory?.invoke(it.toIntroCategory(true))
                    checkIfNextButtonEnabled()
                }
            )

        Column {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BigTitle(text = stringResource(R.string.label_choose_category),
                    modifier = Modifier
                        .padding(top = 20.dp),
                )
                AdviceText(
                    text = stringResource(R.string.label_choose_at_least_one_category),
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 25.dp))
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp, start = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                items(categories, key = { item -> item.id } ) { category ->
                    IntroCategoryItem(
                        category,
                        modifier = Modifier.animateItem()
                    ) { outCategory ->
                        if (category.icon == 0) isDialogShown.value = true
                        else onCategoryClick?.invoke(outCategory)
                        checkIfNextButtonEnabled()
                    }
                }
            }
        }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .background(color = LightMainBackground)
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.button_continue),
                isEnabled = isNextEnabled.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    onNavigateNext?.invoke(
                        categories
                            .filter { it.selection.value }
                            .map { it.toCategoryUI() }
                    )
                }
            )
        }
    }
}

@Composable
private fun IntroCategoryItem(
    category: IntroCategoryUI,
    modifier: Modifier = Modifier,
    onClick: (IntroCategoryUI) -> Unit
) {
    Box(modifier = modifier
        .padding(top = 5.dp)
        .bounceClick(scaledOnPressed = 0.9f) {
            onClick.invoke(category)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .gradient(if (category.selection.value) GradientAccent else GradientMain)
                .padding(16.dp)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    IntroCategoriesContent(state = IntroCategoriesState())
}