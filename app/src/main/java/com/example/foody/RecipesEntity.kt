package com.example.foody

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.models.FoodRecipe
import com.example.foody.util.Constrants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe:FoodRecipe

) {
    @PrimaryKey(autoGenerate = false) // 기본키 중복 x
    var id: Int = 0
}