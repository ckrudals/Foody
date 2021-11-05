package com.example.foody.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.databinding.RecpiesRowLayoutBinding
import com.example.foody.models.FoodRecipe
import com.example.foody.models.Result
import com.example.foody.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    //dataClass 를 emptyList 로 설정
    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecpiesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecpiesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe) {
        // 이전꺼, 데이터가 변한값
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        // 데이터의 차이?? 비교 데이터가 무엇이 변햇는지
        val diffUtilResult= DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        //결과가 나오면 전달
        diffUtilResult.dispatchUpdatesTo(this)
    }
}