package com.example.foody.data

import androidx.lifecycle.LiveData
import com.example.foody.data.database.RecipesDao
import com.example.foody.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// 생성자 주입
class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
     fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    //suspend 를 붙여야하는 이유는?
    suspend fun insertRecipes(recipesEntity: LiveData<List<RecipesEntity>>) {
        recipesDao.insertRecipes(recipesEntity)
    }
}