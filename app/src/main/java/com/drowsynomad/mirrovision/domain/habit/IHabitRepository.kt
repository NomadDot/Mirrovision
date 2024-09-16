package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRegularity
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.domain.models.HabitStatistic
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun updateHabitsColor(categoryId: Int, color: CategoryMainColor)

    suspend fun loadHabitStatistic(habitId: Long): HabitStatistic
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

    override suspend fun updateHabitsColor(categoryId: Int, color: CategoryMainColor) {
        database.habitDao().updateHabitsColor(categoryId, color.toString())
    }

    override suspend fun loadHabitStatistic(habitId: Long): HabitStatistic = withContext(Dispatchers.IO) {
        val habitWithRecordings = database.habitDao().getHabitWithRecordings(habitId)
        val habit = habitWithRecordings?.habit
        val recordings = habitWithRecordings?.habitRecordings

        habit?.let {
             return@withContext HabitStatistic(
                name = it.name,
                description = it.description,
                color = CategoryMainColor.parse(it.bgColor),
                icon = it.icon,
                completedTime = "0",
            )
        }
        HabitStatistic()
    }
}