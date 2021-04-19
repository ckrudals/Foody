package com.example.foody.adapters

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
    private var recipe = emptyList<Result>()

    class MyViewHolder(private val binding: RecpiesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            // 데이터 내부에 변경 사항이있을 때마다 레이아웃을 업데이트함
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
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    fun setData(newData: FoodRecipe) {
        // 이전꺼, 데이터가 변한값
        val recipesDiffUtil = RecipesDiffUtil(recipe, newData.results)
        // 데이터의 차이?? 비교 데이터가 무엇이 변햇는지
        val diffUtilResult= DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData.results
        //결과가 나오면 전달
        diffUtilResult.dispatchUpdatesTo(this)
    }
}