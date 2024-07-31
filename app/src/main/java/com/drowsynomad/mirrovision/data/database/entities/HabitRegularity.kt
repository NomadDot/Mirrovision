package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.drowsynomad.mirrovision.core.emptyString

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
    @ColumnInfo(name = "days")
    val days: List<Int> = emptyList(),
    @ColumnInfo(name = "type")
    val type: String = emptyString()
)