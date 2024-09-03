package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

@Entity(
    tableName = "habits",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = emptyString(),
    @ColumnInfo(name = "description")
    val description: String = emptyString(),
    @ColumnInfo(name = "icon")
    val icon: Int = 0,
    @ColumnInfo(name = "bg_color")
    val bgColor: String = emptyString(),
    @Embedded("amount_")
    val amount: Amount = Amount()
) {
    data class Amount(
        @ColumnInfo(name = "cell")
        val cellAmount: Int = 1,
        @ColumnInfo(name = "filled_cell")
        val prefilledCellAmount: Int = 0
    )

    fun toDomain(): Habit =
        Habit(
            id = id,
            attachedCategoryId = categoryId,
            name = name,
            description = description,
            icon = icon,
            backgroundColor = CategoryMainColor.parse(bgColor),
            cellAmount = amount.cellAmount,
            filledCellAmount = amount.prefilledCellAmount
        )

    fun toUI(): HabitUI { // TODO: insert into domain layer
        val categoryBgColor = CategoryMainColor.parse(bgColor)
        return HabitUI(
            id = id,
            attachedCategoryId = categoryId,
            name = name,
            description = description,
            icon = icon,
            backgroundColor = categoryBgColor,
            stroke = StrokeAmountState(
                cellAmount = amount.cellAmount,
                prefilledCellAmount = amount.prefilledCellAmount,
                filledColor = categoryBgColor.accent
            )
        )
    }
}

data class HabitActivityUpdate(
    @ColumnInfo(name = "id")
    val habitId: Long,
    @ColumnInfo(name = "amount_filled_cell")
    val newFilledCellAmount: Int
)

data class HabitActivityRecordUpdate(
    @ColumnInfo(name = "habit_id")
    val habitId: Long,
    @ColumnInfo(name = "day_date")
    val dayId: Long,
    @ColumnInfo(name = "amount_filled_cell")
    val newFilledCellAmount: Int,
    @ColumnInfo(name = "amount_cell")
    val newCellAmount: Int,
    )

data class HabitUpdate(
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String = emptyString(),
    @ColumnInfo(name = "description")
    val description: String = emptyString(),
    @ColumnInfo(name = "amount_cell")
    val cellAmount: Int,
    @ColumnInfo(name = "amount_filled_cell")
    val filledCellAmount: Int
)