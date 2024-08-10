package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.HabitRegularity
import com.drowsynomad.mirrovision.domain.models.RegularityType

/**
 * @author Roman Voloshyn (Created on 30.07.2024)
 */

@Entity(
    tableName = "habit_regularity",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class HabitRegularity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "habit_id")
    val habitId: Long = 0,
    @ColumnInfo(name = "time")
    val time: String = emptyString(),
    @ColumnInfo(name = "use_Reminder")
    val useReminder: Boolean = false,
    @Embedded("selected_")
    val days: RegularityDays = RegularityDays(),
    @ColumnInfo(name = "type")
    val type: String = emptyString()
) {
    fun toDomain(): HabitRegularity =
        HabitRegularity(
            id = id,
            habitId = habitId,
            time = time,
            useReminder = useReminder,
            selectedDays = days.selectedDays.map { it.toDomain() },
            type = RegularityType.toType(type)
        )
}

data class RegularityDays(
    val selectedDays: List<RegularityDay> = emptyList()
)

data class RegularityDay(
    @ColumnInfo("day_position")
    val dayPosition: Int,
) {
    fun toDomain(): com.drowsynomad.mirrovision.domain.models.RegularityDay =
        com.drowsynomad.mirrovision.domain.models.RegularityDay(dayPosition)
}