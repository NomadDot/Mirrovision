package com.drowsynomad.mirrovision.presentation.screens.introCategories

import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.presentation.core.base.BaseViewModel
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.CategoriesId
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesEvent
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesState
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@HiltViewModel
class IntroCategoriesVM @Inject constructor(
    private val categoryRepository: ICategoryRepository
): BaseViewModel<IntroCategoriesState, IntroCategoriesEvent>() {

    override fun handleUiEvent(uiEvent: IntroCategoriesEvent) {
        when(uiEvent) {
            is IntroCategoriesEvent.LoadLocalizedCategories -> loadCategories()
        }
    }

    private fun loadCategories() {
        updateState {
            it.value = IntroCategoriesState(isProgress = true)
        }

        launch {
            delay(1000L)
            categoryRepository.getCategoriesId()
                .map {
                    it.map {
                        val (nameRes, iconRes) = when(CategoriesId.toEnum(it)) {
                            CategoriesId.CATEGORY_SPORT -> R.string.default_category_sport to R.drawable.ic_category_art
                            CategoriesId.CATEGORY_DIET -> R.string.default_category_diet to R.drawable.ic_category_art
                            CategoriesId.CATEGORY_SELF_EDUCATION -> R.string.default_category_self_education to R.drawable.ic_category_art
                            CategoriesId.CATEGORY_ART -> R.string.default_category_art to R.drawable.ic_category_art
                            CategoriesId.CATEGORY_MEDITATION -> R.string.default_category_meditation to R.drawable.ic_category_art
                            CategoriesId.CATEGORY_WORK -> R.string.default_category_work to R.drawable.ic_category_art
                            CategoriesId.CATEGORY_RELATIONSHIPS -> R.string.default_category_relationship to R.drawable.ic_category_art
                        }
                        IntroCategory(nameRes, iconRes)
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect { categories ->
                    val a = categories.toMutableList() + IntroCategory(R.string.default_category_own)
                    updateState {
                        it.value = IntroCategoriesState(categories = a)
                    }
                }
        }
    }
}