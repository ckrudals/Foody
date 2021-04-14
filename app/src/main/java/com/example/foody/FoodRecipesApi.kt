package com.example.foody

import com.example.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    //서버 주소
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        //QueryMap 형식으로 Data 를 받는다.
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

}