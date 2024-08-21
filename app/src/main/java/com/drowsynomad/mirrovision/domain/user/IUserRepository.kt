package com.drowsynomad.mirrovision.domain.user

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences.Companion.IS_PRESET_CONFIGURED
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences.Companion.THEME
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences.Companion.USER_LANGUAGE
import com.drowsynomad.mirrovision.domain.models.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

interface IUserRepository {
    suspend fun setUserFinishPreset()
    fun doesUserFinishPreset(): Flow<Boolean>

    fun getUserLanguageId(defaultLocaleId: String): Flow<String>
    fun getSupportedLanguagesWithUserSelected(): Flow<List<Locale>>
    suspend fun saveUserLanguage(locale: String)

    fun isDarkMode(): Flow<Boolean>
    suspend fun changeUserTheme(darkMode: Boolean)
}

class UserRepository(
    private val dataStore: DataStorePreferences,
    private val assetStore: IAssetStore
) : IUserRepository {
    override suspend fun setUserFinishPreset() {
        dataStore.saveData(IS_PRESET_CONFIGURED, true)
    }

    override fun doesUserFinishPreset(): Flow<Boolean> =
        dataStore.getDataStoreFlow(IS_PRESET_CONFIGURED)
            .map { it ?: false}

    override fun getUserLanguageId(defaultLocaleId: String): Flow<String> =
        dataStore.getDataStoreFlow(USER_LANGUAGE)
            .map { it ?: defaultLocaleId }

    override suspend fun changeUserTheme(darkMode: Boolean) {
        dataStore.saveData(THEME, darkMode)
    }

    override fun getSupportedLanguagesWithUserSelected(): Flow<List<Locale>> {
        return dataStore
            .getDataStoreFlow(DataStorePreferences.USER_LANGUAGE)
            .zip(assetStore.getSupportedLanguages()) { userLanguage, allLanguages ->
                val languages = allLanguages
                    .map { Locale(it, false) }
                languages.find { userLanguage == it.id }?.isSelected = true
                languages
            }
    }

    override suspend fun saveUserLanguage(locale: String) {
        dataStore.saveData(USER_LANGUAGE, locale)
    }

    override fun isDarkMode(): Flow<Boolean> {
        return dataStore.getDataStoreFlow(THEME)
            .map { it ?: false }
    }
}

