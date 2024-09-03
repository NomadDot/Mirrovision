package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRegularity
import com.drowsynomad.mirrovision.domain.models.Habit

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

interface IHabitRepository {
    fun updateActivityCell(
        habitId: Long,
        newFilledCount: Int,
        dayId: Long
    )
    suspend fun createNewOrUpdateHabit(habit: Habit)

    suspend fun loadHabitWithRegularity(habitId: Long): HabitWithRegularity
}

class HabitRepository(
    private val database: MirrovisionDatabase
): IHabitRepository {
    override fun updateActivityCell(habitId: Long, newFilledCount: Int, dayId: Long) {
        database.habitDao()
            .updateHabitRecordActivity(habitId, dayId, newFilledCount)
    }

    override suspend fun createNewOrUpdateHabit(habit: Habit) {
        if(database.habitDao().getHabitCount(habit.id) < 1)
            database.habitDao().insertHabit(habit.toData())
        else
            database.habitDao().updateHabitEntity(habit.toUpdateModel())
    }

    override suspend fun loadHabitWithRegularity(habitId: Long): HabitWithRegularity {
        return database.habitDao().getHabitWithRegularity(habitId)
    }
}