package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.data.database.entities.tuples.HabitWithRecordings
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.domain.models.RegularityType
import kotlinx.coroutines.flow.map

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

interface IHabitRecordingRepository {
    suspend fun isDailyRecordConfigured(dayId: Long): Boolean

    suspend fun createDailyRecordings(
        dayId: Long,
        currentDay: Int,
        typeOfDay: RegularityType
    )

    suspend fun loadDailyRecordings(): List<HabitWithRecordings>
}

class HabitRecordingRepository(
    private val database: MirrovisionDatabase
): IHabitRecordingRepository {
    override suspend fun isDailyRecordConfigured(dayId: Long): Boolean =
        database.habitDao().getRecords(dayId).isNotEmpty()

    override suspend fun createDailyRecordings(
        dayId: Long,
        currentDay: Int,
        typeOfDay: RegularityType
    ) {
        val todayHabits = mutableListOf<Habit>()

       /* database.habitDao().getHabitWithRegularity()
            .map { habitsWithRegularity ->
                habitsWithRegularity.forEach { habitWithRegularity ->
                    val regularity = habitWithRegularity?.habitRegularity
                    val habit = habitWithRegularity?.habit

                    // TODO:
             *//*       regularity?.let {
                        it.find { it.days.regularityDays? == true }
                            ?.let {
                                todayHabits.add(habitWithRegularity.habit.toDomain())
                            }
                        return@forEach
                    }*//*

                    habit?.toDomain()?.let { todayHabits.add(it) }
                }

                todayHabits
            }
            .collect { habits ->
                habits.map {
                    HabitRecord(
                        habitId = it.id,
                        date = dayId,
                        amount = HabitEntity.Amount(
                            cellAmount = it.cellAmount,
                            prefilledCellAmount = it.filledCellAmount
                        )
                    )
                }
            }*/
    }

    override suspend fun loadDailyRecordings(): List<HabitWithRecordings> {
        TODO("Not yet implemented")
    }
}