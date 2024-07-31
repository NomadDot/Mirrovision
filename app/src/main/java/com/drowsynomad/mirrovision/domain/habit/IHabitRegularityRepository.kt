package com.drowsynomad.mirrovision.domain.habit

import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

interface IHabitRegularityRepository {

}

class HabitRegularityRepository(
    private val database: MirrovisionDatabase
): IHabitRegularityRepository {

}