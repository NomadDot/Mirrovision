package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.domain.models.HabitRegularities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

interface IHabitRegularityRepository {
    suspend fun createOrUpdateRegularity(regularity: HabitRegularities)

    fun loadRegularityForHabit(
        habitId: Long,
    ): Flow<HabitRegularities>
}

class HabitRegularityRepository(
    private val database: MirrovisionDatabase,
): IHabitRegularityRepository {
    override suspend fun createOrUpdateRegularity(regularity: HabitRegularities) {
        database.habitDao()
            .insertHabitRegularity(regularity.regularities.map { it.toData() })
    }

    override fun loadRegularityForHabit(
        habitId: Long
    ): Flow<HabitRegularities> =
        database.habitDao().getHabitRegularities(habitId)
            .map { list ->
                HabitRegularities(
                    list.map { reg -> reg.toDomain() }
                )
            }
}
