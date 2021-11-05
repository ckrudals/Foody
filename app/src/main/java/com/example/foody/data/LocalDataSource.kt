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
    //flow 와 LiveData는 다르다
     fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    //suspend 를 붙여야하는 이유는? -> 비동기 처리 가능하게 하기위해
    suspend fun insertRecipes(recipesEntity:RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}