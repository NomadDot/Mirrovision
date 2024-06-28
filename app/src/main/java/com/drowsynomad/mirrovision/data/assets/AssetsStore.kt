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
}

class AssetsStore(
    private val context: Context
): IAssetStore {
    companion object {
        private const val CATEGORIES_PRESET = "preset_categories.json"

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
}