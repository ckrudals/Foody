package com.example.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foody.data.database.RecipesEntity
import com.example.foody.models.FoodRecipe
import com.example.foody.util.NetworkResult
import retrofit2.Response

class RecipesBinding {

    companion object {

        // error 뜨면 이미지를 숨긴다.

        // requireAll= true 컴파일러가 경고 또는 오류 뜨게한다
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe?>?,
            database: List<RecipesEntity>?
        ) {

            // network 상태에 따라 나눈다
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {

                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE

            } else if (apiResponse is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse2","readDatabase2",requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe?>?,
            database: List<RecipesEntity>?
        ) {
            // network 상태에 따라 나눈다
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {

                textView.visibility = View.VISIBLE
                textView.text=apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE

            } else if (apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }
        }
    }
}