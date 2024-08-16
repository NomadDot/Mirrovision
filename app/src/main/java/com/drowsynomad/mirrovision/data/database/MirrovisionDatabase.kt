package com.drowsynomad.mirrovision.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.drowsynomad.mirrovision.data.database.dao.CategoryDao
import com.drowsynomad.mirrovision.data.database.dao.HabitDao
import com.drowsynomad.mirrovision.data.database.entities.CategoryEntity
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.data.database.entities.HabitRecord
import com.drowsynomad.mirrovision.data.database.entities.HabitRegularity

/**
 * @author Roman Voloshyn (Created on 21.06.2024)
 */

@Database(
    version = 1,
    entities = [
        HabitEntity::class,
        HabitRegularity::class,
        HabitRecord::class,
        CategoryEntity::class
    ]
)
@TypeConverters(value = [TypeConverter::class])
abstract class MirrovisionDatabase: RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var databaseInstance: MirrovisionDatabase? = null

        fun getInstance(context: Context): MirrovisionDatabase {
            if(databaseInstance == null) {
                synchronized(this) {
                    databaseInstance = Room
                        .databaseBuilder(context, MirrovisionDatabase::class.java, "mirrovision_db")
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return databaseInstance as MirrovisionDatabase
        }
    }
}