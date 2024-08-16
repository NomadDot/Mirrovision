package com.drowsynomad.mirrovision.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.drowsynomad.mirrovision.data.database.entities.CategoryAndHabits
import com.drowsynomad.mirrovision.data.database.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryEntity>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesAndHabits(): Flow<List<CategoryAndHabits>>
}