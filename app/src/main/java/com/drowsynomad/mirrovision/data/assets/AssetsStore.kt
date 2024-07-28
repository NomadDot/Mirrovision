package com.drowsynomad.mirrovision.data.assets

import android.content.Context
import com.drowsynomad.mirrovision.domain.models.StringId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

/**
 * @author Roman Voloshyn (Created on 28.06.2024)
 */

interface IAssetStore {
    fun getCategoriesId(): Flow<List<StringId>>
    fun getSupportedLanguages(): Flow<List<String>>
}

class AssetsStore(
    private val context: Context
): IAssetStore {
    companion object {
        private const val CATEGORIES_PRESET = "preset_categories.json"
        private const val SUPPORTED_LANGUAGES = "supported_languages.json"

        fun getInstance(context: Context): AssetsStore {
            return AssetsStore(context)
        }
    }

    override fun getCategoriesId(): Flow<List<StringId>> = flow {
        val rawData = context.assets.open(CATEGORIES_PRESET)
            .bufferedReader()
            .use { it.readText() }
        emit(Json.decodeFromString<List<StringId>>(rawData))
    }

    override fun getSupportedLanguages(): Flow<List<String>> {
        return flow {
            val rawData = context.assets.open(SUPPORTED_LANGUAGES)
                .bufferedReader()
                .use { it.readText() }
            emit(Json.decodeFromString<List<StringId>>(rawData).map { it.id })
        }
    }
}