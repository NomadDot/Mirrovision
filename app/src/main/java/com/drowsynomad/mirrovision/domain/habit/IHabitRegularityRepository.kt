package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.domain.models.HabitRegularity

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

interface IHabitRegularityRepository {
    suspend fun createOrUpdateRegularity(regularity: List<HabitRegularity>)
}

class HabitRegularityRepository(
    private val database: MirrovisionDatabase
): IHabitRegularityRepository {
    override suspend fun createOrUpdateRegularity(regularity: List<HabitRegularity>) =
        database.habitDao()
            .insertHabitRegularity(regularity.map { it.toData() })
}
