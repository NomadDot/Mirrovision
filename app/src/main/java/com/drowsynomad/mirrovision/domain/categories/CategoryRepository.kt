package com.drowsynomad.mirrovision.domain.categories

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.CategoryAndHabits
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.domain.models.StringId
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

interface ICategoryRepository {
    fun getCategoriesId(): Flow<List<StringId>>

    suspend fun saveCategoriesPreset(categories: List<Category>, habits: List<Habit>)
    fun loadLocalCategories(): Flow<List<CategoryAndHabits>>
}

class CategoryRepository(
    private val assetStore: IAssetStore,
    private val database: MirrovisionDatabase
): ICategoryRepository {
    override fun getCategoriesId(): Flow<List<StringId>> = assetStore.getCategoriesId()

    override suspend fun saveCategoriesPreset(
        categories: List<Category>,
        habits: List<Habit>
    ) {
        val categoryDao = database.categoryDao()
        val habitDao = database.habitDao()

        categories.forEach {
            val entity = it.toCategoryEntity()
            categoryDao.insertCategory(entity)
        }

        habits.forEach {
            val entity = it.toHabitEntity()
            habitDao.insertHabit(entity)
        }
    }

    override fun loadLocalCategories(): Flow<List<CategoryAndHabits>> =
        database.categoryDao().getCategoriesAndHabits()
}