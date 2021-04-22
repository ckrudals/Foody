package com.example.foody

import androidx.room.TypeConverter
import com.example.foody.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

//서버에서 받아온 데이터의 타입을 변환해준다
// json -> toString
class RecipesTypeConverter {

    //gson -> java 객체를 JSON으로 표현
    var gson = Gson()


    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {

        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        //typeToken 이란
        //모르겟다 말그대로 토큰을 바꿔준다?? 왜 ??
        // data를 gson 형삭으로 매핑하기 위해 ?
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }
}