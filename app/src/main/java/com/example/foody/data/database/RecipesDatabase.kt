package com.example.foody.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [RecipesEntity::class],
    // 스키마 변경할때마다 버전을 올려야한다.
    version = 1,
    exportSchema = false
)

@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}