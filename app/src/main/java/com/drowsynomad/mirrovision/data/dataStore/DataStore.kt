package com.drowsynomad.mirrovision.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Roman Voloshyn (Created on 18.07.2024)
 */

class DataStorePreferences(
    private val context: Context
) {
    companion object {
        val IS_PRESET_CONFIGURED = booleanPreferencesKey(name = "IS_PRESET_CONFIGURED")
        val USER_LANGUAGE = stringPreferencesKey(name = "USER_LANGUAGE")
        val THEME = booleanPreferencesKey(name = "THEME")
    }

    fun <T> getDataStoreFlow(key: Preferences.Key<T>): Flow<T?>  =
        context.dataStore.data
            .map { preferences -> preferences[key] }

    suspend fun <T> saveData(key: Preferences.Key<T>, value: T) = context.dataStore.edit {
        it[key] = value
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
