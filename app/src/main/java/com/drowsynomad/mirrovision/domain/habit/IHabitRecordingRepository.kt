package com.drowsynomad.mirrovision.domain.habit

import android.util.Log
import com.drowsynomad.mirrovision.core.generateDayId
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRecordings
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.domain.models.RegularityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

interface IHabitRecordingRepository {
    suspend fun isDailyRecordNotConfigured(dayId: Long): Boolean

    suspend fun createDailyRecordings(
        dayId: Long = generateDayId(),
        currentDayInWeek: Int,
        currentDayInMonth: Int,
    )

    suspend fun updateRecordingForToday(
        dayId: Long = generateDayId(),
        habitId: Long,
        cellAmount: Int,
        filledCellAmount: Int
    )

    suspend fun removeRecordingForToday(
        dayId: Long = generateDayId(),
        habitId: Long
    )

    suspend fun loadDailyRecordings(): List<HabitWithRecordings>
    suspend fun getTodayRecording(dayId: Long, habitId: Long): HabitRecord?
}

class HabitRecordingRepository(
    private val database: MirrovisionDatabase
): IHabitRecordingRepository {
    private val habitDao by lazy { database.habitDao() }
    
    override suspend fun isDailyRecordNotConfigured(dayId: Long): Boolean =
        habitDao.getRecords(dayId).isEmpty()

    override suspend fun createDailyRecordings(
        dayId: Long,
        currentDayInWeek: Int,
        currentDayInMonth: Int,
    ) = withContext(Dispatchers.IO) {
        val todayHabits = mutableListOf<Habit>()

        val allHabits = habitDao.getHabitsWithRegularity()

        allHabits.forEach {
            val habit = it.habit
            val regularities = it.habitRegularity

            regularities.forEach { dataRegularity ->
                val regularity = dataRegularity.toDomain()
                if(
                    regularity.selectedDays.find {
                        it.dayPosition ==
                            if(regularity.type is RegularityType.MonthlyType) currentDayInMonth
                            else currentDayInWeek } != null
                ) {
                    todayHabits.add(habit.toDomain())
                }
            }
        }

        habitDao.insertHabitRecords(
            todayHabits.map {
                HabitRecord(
                    habitId = it.id,
                    date = dayId,
                    amount = HabitEntity.Amount(it.cellAmount, it.filledCellAmount)
                )
            }
        )
    }

    override suspend fun updateRecordingForToday(
        dayId: Long,
        habitId: Long,
        cellAmount: Int,
        filledCellAmount: Int
    ) {
        val count = habitDao.getRecordCount(dayId, habitId)
        Log.i("RECORD", count.toString())
        if(count < 1) {
            habitDao.insertHabitRecord(
                HabitRecord(
                    habitId = habitId,
                    date = dayId,
                    amount = HabitEntity.Amount(cellAmount, filledCellAmount)
                )
            )
        } else {
            habitDao.updateHabitRecord(
                habitId = habitId,
                dayId = dayId,
                newCellAmount = cellAmount,
                newFilledCellAmount = filledCellAmount
            )
        }
    }

    override suspend fun removeRecordingForToday(dayId: Long, habitId: Long) {
        if(habitDao.getRecordCount(dayId, habitId) == 1) {
            habitDao.deleteRecording(dayId, habitId)
        }
    }

    override suspend fun loadDailyRecordings(): List<HabitWithRecordings> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayRecording(dayId: Long, habitId: Long): HabitRecord? =
        habitDao.getRecord(dayId, habitId)
}