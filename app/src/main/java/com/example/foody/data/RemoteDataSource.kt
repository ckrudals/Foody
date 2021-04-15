package com.example.foody.data

import com.example.foody.data.network.FoodRecipesApi
import com.example.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(

    // 이코드를 썻을 뿐인데 어떻게 NetworkModule Object 에 있는거라고 알 수 있지 ..?
    private val foodRecipesApi: FoodRecipesApi,

    ) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}