package com.drowsynomad.mirrovision.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drowsynomad.mirrovision.core.emptyString

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

@Entity(
    tableName = "categories"
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = emptyString(),
    val icon: Int = 0,
    val bgColor: String = emptyString()
)