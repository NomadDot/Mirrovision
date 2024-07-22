package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.drowsynomad.mirrovision.core.emptyString

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
}

data class HabitActivityUpdate(
    @ColumnInfo(name = "id")
    val habitId: Long,
    @ColumnInfo(name = "amount_filled_cell")
    val newFilledCellAmount: Int
)