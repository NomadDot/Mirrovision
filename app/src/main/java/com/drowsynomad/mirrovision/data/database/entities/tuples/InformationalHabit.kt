package com.drowsynomad.mirrovision.data.database.entities.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.data.database.entities.HabitRegularity

/**
 * @author Roman Voloshyn (Created on 30.07.2024)
 */

data class HabitWithRegularity(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id",
    )
    val habitRegularity: List<HabitRegularity>
)

data class HabitWithRecordings(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id",
    )
    val habitRecordings: List<HabitRecord>
)

data class RecordingWithHabit(
    @Embedded
    val habitRecordings: HabitRecord,
    @Relation(
        parentColumn = "habit_id",
        entityColumn = "id",
    )
    val habit: HabitEntity,
)

data class FullInfoHabit(
    @Embedded val habit: HabitEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id",
    )
    val habitRegularity: List<HabitRegularity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id",
    )
    val habitRecordings: List<HabitRecord>
)