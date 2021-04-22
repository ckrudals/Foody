package com.example.foody

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE ) //데이터가 추가할때마다 수정
    suspend fun insertRecipes(recipesEntity: RecipesEntity)


    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>



}