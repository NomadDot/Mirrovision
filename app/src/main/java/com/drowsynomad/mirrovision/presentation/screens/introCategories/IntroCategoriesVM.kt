package com.drowsynomad.mirrovision.presentation.screens.introCategories

import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.language.ILanguageRepository
import com.drowsynomad.mirrovision.domain.user.IUserRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.CategoriesId
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesEvent
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoriesState
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoryUI
import com.drowsynomad.mirrovision.presentation.utils.IStringConverterManager
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
    private val stringManager: IStringConverterManager,
    private val categoryRepository: ICategoryRepository,
    private val userRepository: IUserRepository
): StateViewModel<IntroCategoriesState, IntroCategoriesEvent, SideEffect>(
    IntroCategoriesState(isProgress = true)
) {
    override fun handleUiEvent(uiEvent: IntroCategoriesEvent) {
        when(uiEvent) {
            is IntroCategoriesEvent.LoadLocalizedCategories -> loadCategoriesPreset()
            is IntroCategoriesEvent.SelectCategory -> clickOnCategory(uiEvent.category)
            is IntroCategoriesEvent.InsertCustomCategory -> insertCustomCategory(uiEvent.category)
            is IntroCategoriesEvent.SetUserLocale -> saveUserLocalization(uiEvent.locale)
        }
    }

    private fun saveUserLocalization(pref: String) {
        launch {
            userRepository.saveUserLanguage(pref)
        }
    }

    private fun clickOnCategory(introCategoryUI: IntroCategoryUI) {
        uiState.value.categories.find { it == introCategoryUI }?.selection?.value = !introCategoryUI.selection.value
    }

    private fun insertCustomCategory(introCategoryUI: IntroCategoryUI) {
        val lastPosition = uiState.value.categories.lastIndex
        uiState.value.categories.add(lastPosition, introCategoryUI)
    }

    private fun loadCategoriesPreset() {
        if(uiState.value.categories.isEmpty())
            launch {
                delay(300L)
                categoryRepository.getCategoriesId()
                    .map {
                        it.map { stringId ->
                            val (name: String?, iconRes: Int) = when(CategoriesId.toEnum(stringId)) {
                                CategoriesId.CATEGORY_SPORT -> stringManager.getString(R.string.default_category_sport) to R.drawable.ic_sport_ball
                                CategoriesId.CATEGORY_DIET -> stringManager.getString(R.string.default_category_diet) to R.drawable.ic_diet_pizza
                                CategoriesId.CATEGORY_SELF_EDUCATION -> stringManager.getString(R.string.default_category_self_education) to R.drawable.ic_self_education_books
                                CategoriesId.CATEGORY_ART -> stringManager.getString(R.string.default_category_art) to R.drawable.ic_art_camera
                                CategoriesId.CATEGORY_MEDITATION -> stringManager.getString(R.string.default_category_meditation) to R.drawable.ic_art_camera
                                CategoriesId.CATEGORY_WORK -> stringManager.getString(R.string.default_category_work) to R.drawable.ic_art_camera
                                CategoriesId.CATEGORY_RELATIONSHIPS -> stringManager.getString(R.string.default_category_relationship) to R.drawable.ic_art_camera
                                CategoriesId.CATEGORY_NONE -> null to 0
                            }
                            IntroCategoryUI(name ?: emptyString(), icon = iconRes)
                        }
                    }
                    .flowOn(Dispatchers.IO)
                    .collect { categories ->
                        val finalData = categories.filter { it.name.isNotEmpty() } + IntroCategoryUI(stringManager.getString(R.string.default_category_own))
                        uiState.value.categories.addAll(finalData)
                        uiState.value = uiState.value.copy(isProgress = false)
                    }
            }
    }
}