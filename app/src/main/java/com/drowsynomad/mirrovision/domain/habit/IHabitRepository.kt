package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.HabitActivityUpdate
import com.drowsynomad.mirrovision.domain.models.Habit

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

interface IHabitRepository {
    fun updateActivityCell(habitId: Long, newFilledCount: Int)
    suspend fun createNewOrUpdateHabit(habit: Habit)
}

class HabitRepository(
    private val database: MirrovisionDatabase
): IHabitRepository {
    override fun updateActivityCell(habitId: Long, newFilledCount: Int) {
        database.habitDao()
            .updateHabitActivity(HabitActivityUpdate(habitId, newFilledCount))
    }

    override suspend fun createNewOrUpdateHabit(habit: Habit) {
        database
            .habitDao()
            .insertHabit(habit.toData())
    }
}