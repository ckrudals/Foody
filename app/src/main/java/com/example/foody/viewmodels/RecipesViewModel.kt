package com.example.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foody.util.Constrants
import com.example.foody.util.Constrants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foody.util.Constrants.Companion.QUERY_API_KEY
import com.example.foody.util.Constrants.Companion.QUERY_NUMBER
import com.example.foody.util.Constrants.Companion.QUERY_TYPE
import com.example.foody.util.Constrants.Companion.QUERY_fill_ingredients

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

     fun applyQueries(): HashMap<String, String> {
         // ViewModel
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constrants.API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "vegan"
        queries[QUERY_fill_ingredients] = "true"

        return queries

    }
}