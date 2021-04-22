package com.example.foody

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[RecipesEntity::class],
    // 스키마 변경할때마다 버전을 올려야한다.
    version=1
)
abstract class RecipesDatabase : RoomDatabase() {
}