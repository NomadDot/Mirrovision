package com.drowsynomad.mirrovision.domain.user

import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences.Companion.IS_PRESET_CONFIGURED
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences.Companion.USER_LANGUAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

interface IUserRepository {
    suspend fun setUserFinishPreset()
    fun doesUserFinishPreset(): Flow<Boolean>

    fun getUserLanguageId(defaultLocaleId: String): Flow<String>
}

class UserRepository(private val dataStore: DataStorePreferences) : IUserRepository {
    override suspend fun setUserFinishPreset() {
        dataStore.saveData(IS_PRESET_CONFIGURED, true)
    }

    override fun doesUserFinishPreset(): Flow<Boolean> =
        dataStore.getDataStoreFlow(IS_PRESET_CONFIGURED)
            .map { it ?: false}

    override fun getUserLanguageId(defaultLocaleId: String): Flow<String> =
        dataStore.getDataStoreFlow(USER_LANGUAGE)
            .map { it ?: defaultLocaleId }
}

