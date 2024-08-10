package com.drowsynomad.mirrovision.domain.categories

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.CategoryAndHabits
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.domain.models.HabitRegularities
import com.drowsynomad.mirrovision.domain.models.StringId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

interface ICategoryRepository {
    fun getCategoriesId(): Flow<List<StringId>>

    suspend fun saveCategoriesPreset(
        categories: List<Category>,
        habits: List<Habit>,
        habitRegularities: List<HabitRegularities>
    )
    fun loadLocalCategoriesWithHabits(): Flow<List<CategoryAndHabits>>
    fun loadLocalCategories(): Flow<List<Category>>
}

class CategoryRepository(
    private val assetStore: IAssetStore,
    private val database: MirrovisionDatabase
): ICategoryRepository {
    override fun getCategoriesId(): Flow<List<StringId>> = assetStore.getCategoriesId()

    override suspend fun saveCategoriesPreset(
        categories: List<Category>,
        habits: List<Habit>,
        habitRegularities: List<HabitRegularities>
    ) {
        val categoryDao = database.categoryDao()
        val habitDao = database.habitDao()

        categories.forEach {
            val entity = it.toCategoryEntity()
            categoryDao.insertCategory(entity)
        }

        habits.forEachIndexed { index, habit ->
            val entity = habit.toData()
            habitDao.insertHabit(entity)

            val habitRecords = habitRegularities[index].regularities.map { it.toData() }
            habitDao.insertHabitRegularity(habitRecords)
        }
    }

    override fun loadLocalCategoriesWithHabits(): Flow<List<CategoryAndHabits>> =
        database.categoryDao().getCategoriesAndHabits()

    override fun loadLocalCategories(): Flow<List<Category>> =
        database.categoryDao().getAllCategories().map { entityCategories ->
            entityCategories.map { it.toDomain()}
        }
}