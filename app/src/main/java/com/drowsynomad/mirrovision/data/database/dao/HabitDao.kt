package com.drowsynomad.mirrovision.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.drowsynomad.mirrovision.data.database.entities.HabitActivityUpdate
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.data.database.entities.tuples.FullInfoHabit
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRecordings
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRegularity
import kotlinx.coroutines.flow.Flow

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity?>>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getFullInformationalHabits(): Flow<List<FullInfoHabit?>>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getHabitWithRecordings(): Flow<List<HabitWithRecordings?>>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getHabitWithRegularity(): Flow<List<HabitWithRegularity?>>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getFullInfoHabitById(habitId: Long): FullInfoHabit?

    @Update(entity = HabitEntity::class)
    fun updateEntity(habit: HabitEntity)

    @Update(entity = HabitEntity::class)
    fun updateHabitActivity(updateEntity: HabitActivityUpdate)
}