package com.drowsynomad.mirrovision.domain.language

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences
import com.drowsynomad.mirrovision.domain.models.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

interface ILanguageRepository {
    fun getSupportedLanguagesWithUserSelected(): Flow<List<Locale>>
}

class LanguageRepository(
    private val dataStorePreferences: DataStorePreferences,
    private val assetStore: IAssetStore,
) : ILanguageRepository {
    override fun getSupportedLanguagesWithUserSelected(): Flow<List<Locale>> {
        return dataStorePreferences
            .getDataStoreFlow(DataStorePreferences.USER_LANGUAGE)
            .zip(assetStore.getSupportedLanguages()) { userLanguage, allLanguages ->
                val languages = allLanguages
                    .map { Locale(it, false) }
                languages.find { userLanguage == it.id }?.isSelected = true
                languages
            }
    }
}