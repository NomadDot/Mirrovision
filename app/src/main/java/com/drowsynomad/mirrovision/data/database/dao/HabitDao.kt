package com.drowsynomad.mirrovision.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.drowsynomad.mirrovision.data.database.entities.HabitActivityUpdate
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.data.database.entities.HabitRegularity
import com.drowsynomad.mirrovision.data.database.entities.HabitUpdate
import com.drowsynomad.mirrovision.data.database.entities.tuples.FullInfoHabit
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRecordings
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRegularity
import com.drowsynomad.mirrovision.data.database.entities.tuples.RecordingWithHabit
import kotlinx.coroutines.flow.Flow

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = HabitRegularity::class)
    fun insertHabitRegularity(habits: List<HabitRegularity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = HabitRecord::class)
    suspend fun insertHabitRecords(habits: List<HabitRecord>)

    @Insert(onConflict = OnConflictStrategy.ABORT, entity = HabitRecord::class)
    fun insertHabitRecord(habits: HabitRecord)

    @Query("SELECT COUNT(*) FROM habits WHERE id = :habitId")
    fun getHabitCount(habitId: Long): Int

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity?>>

    @Query("SELECT * FROM habit_regularity as reg WHERE reg.habit_id = :habitId")
    fun getHabitRegularities(habitId: Long): Flow<List<HabitRegularity>>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getFullInformationalHabits(): Flow<List<FullInfoHabit?>>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getHabitsWithRecordings(): List<HabitWithRecordings?>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitWithRecordings(habitId: Long): HabitWithRecordings?

    @Transaction
    @Query("SELECT * FROM habits AS habit " +
            "JOIN habit_record AS record ON habit.id = record.habit_id " +
            "AND record.day_date >= :start")
    fun getRecordingsWithHabitOnPeriod(start: Long): Map<HabitEntity, List<HabitRecord>>

    @Transaction
    @Query("SELECT * FROM habit_record AS record" +
            " WHERE record.day_date >= :start" +
            " AND record.day_date <= :end" +
            " AND record.habit_id = :habitId")
    suspend fun getRecordingsOnPeriod(start: Long, end: Long, habitId: Long): List<HabitRecord>

    @Query("SELECT * FROM habit_record WHERE day_date = :dayId AND habit_id = :habitId")
    fun getRecord(dayId: Long, habitId: Long): HabitRecord?

    @Transaction
    @Query("SELECT * FROM habit_record WHERE day_date = :dayId")
    suspend fun getRecordingWithHabit(dayId: Long): List<RecordingWithHabit?>

    @Query("SELECT * FROM habit_record WHERE day_date = :dayId")
    fun getHabitRecordingsByDayId(dayId: Long): List<HabitRecord?>

    @Query("SELECT COUNT(*) FROM habit_record WHERE day_date = :dayId AND habit_id = :habitId")
    suspend fun getRecordCount(dayId: Long, habitId: Long): Int

    @Transaction
    @Query("SELECT * FROM habits")
    suspend fun getHabitsWithRegularity(): List<HabitWithRegularity>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitWithRegularity(habitId: Long): HabitWithRegularity

    @Transaction
    @Query("SELECT * FROM habits")
    suspend fun getHabitsRegularity(): List<HabitWithRegularity?>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getFullInfoHabitById(habitId: Long): FullInfoHabit?

    @Query("SELECT * FROM habit_record WHERE day_date = :dayId")
    suspend fun getRecords(dayId: Long): List<HabitRecord>

    @Update(entity = HabitEntity::class)
    fun updateHabitEntity(habit: HabitUpdate)

    @Update(entity = HabitEntity::class)
    fun updateHabitActivity(updateEntity: HabitActivityUpdate)

    @Query("UPDATE habit_record" +
            " SET amount_filled_cell = :newFilledCellAmount" +
            " WHERE day_date = :dayId AND habit_id = :habitId")
    fun updateHabitRecordActivity(
        habitId: Long,
        dayId: Long,
        newFilledCellAmount: Int
    )

    @Query("UPDATE habit_record" +
            " SET amount_filled_cell = :newFilledCellAmount, amount_cell = :newCellAmount" +
            " WHERE day_date = :dayId AND habit_id = :habitId")
    fun updateHabitRecord(
        habitId: Long,
        dayId: Long,
        newCellAmount: Int,
        newFilledCellAmount: Int
    )

    @Query("DELETE FROM habit_record WHERE day_date = :dayId AND habit_id = :habitId")
    suspend fun deleteRecording(dayId: Long, habitId: Long)

    @Query("UPDATE habits SET bg_color = :newColor WHERE category_id = :categoryId")
    fun updateHabitsColor(categoryId: Int, newColor: String)
}