package com.drowsynomad.mirrovision.domain.language

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences.Companion.USER_LANGUAGE
import com.drowsynomad.mirrovision.domain.models.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

interface ILanguageRepository {

}

class LanguageRepository(
    private val dataStorePreferences: DataStorePreferences,
    private val assetStore: IAssetStore,
) : ILanguageRepository {

}