package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.HabitActivityUpdate

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

interface IHabitRepository {
    fun updateActivityCell(habitId: Long, newFilledCount: Int)
}

class HabitRepository(
    private val database: MirrovisionDatabase
): IHabitRepository {
    override fun updateActivityCell(habitId: Long, newFilledCount: Int) {
        database.habitDao()
            .updateHabitActivity(HabitActivityUpdate(habitId, newFilledCount))
    }
}