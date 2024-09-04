package com.drowsynomad.mirrovision.domain.categories

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.CategoryAndHabits
import com.drowsynomad.mirrovision.data.database.entities.CategoryUpdateEntity
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.domain.models.HabitRegularities
import com.drowsynomad.mirrovision.domain.models.StringId
import com.drowsynomad.mirrovision.presentation.core.components.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.components.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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
    suspend fun loadCategories(): List<Category>
    fun loadLocalCategoriesWithHabits(): Flow<List<CategoryAndHabits>>
    suspend fun loadCategoriesForDay(dayId: Long): List<CategoryUI>

    suspend fun updateCategory(
        categoryId: Int,
        newColor: CategoryMainColor,
        newIcon: Int,
        newName: String
    )
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

    override suspend fun loadCategories(): List<Category> {
        return database.categoryDao().getAllCategories().map { it.toDomain() }
    }

    override fun loadLocalCategoriesWithHabits(): Flow<List<CategoryAndHabits>> =
        database.categoryDao().getCategoriesAndHabits()

    override suspend fun loadCategoriesForDay(
        dayId: Long
    ): List<CategoryUI> = withContext(Dispatchers.IO) {
        val categories = database.categoryDao().getAllCategories()
        val recordingsWithHabits = database.habitDao().getRecordingWithHabit(dayId)

        categories
            .map { it.toDomain() }
            .map { categoryDomain ->
                val habits = recordingsWithHabits
                    .map { it?.habit to it?.habitRecordings }
                    .filter { it.first?.categoryId == categoryDomain.id }
                    .map {
                        val habit = it.first
                        val recording = it.second
                        val strokeAmount = recording?.amount

                        val habitUI = habit?.toUI()
                        habitUI
                            ?.copy(
                                stroke = StrokeAmountState(
                                    cellAmount = strokeAmount?.cellAmount ?: 1,
                                    prefilledCellAmount = strokeAmount?.prefilledCellAmount ?: 0,
                                    filledColor = habitUI.accentColor
                                )
                            )
                    }

                categoryDomain.toCategoryUI(habits.filterNotNull())
            }.sortedBy { it.habits.isEmpty() }
    }

    override suspend fun updateCategory(
        categoryId: Int,
        newColor: CategoryMainColor,
        newIcon: Int,
        newName: String
    ) {
        database.categoryDao()
            .updateCategory(
                CategoryUpdateEntity(categoryId, newName, newIcon, newColor.toString())
            )
    }
}