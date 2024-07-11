package com.drowsynomad.mirrovision.domain.categories

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.domain.models.StringId
import kotlinx.coroutines.flow.Flow

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

interface ICategoryRepository {
    fun getCategoriesId(): Flow<List<StringId>>
}

class CategoryRepository(
    private val assetStore: IAssetStore
): ICategoryRepository {
    override fun getCategoriesId(): Flow<List<StringId>> = assetStore.getCategoriesId()
}