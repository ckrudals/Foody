package com.example.foody.util

import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil(
    private val oldList: List<com.example.foody.models.Result>,
    private val newList: List<com.example.foody.models.Result>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        // 함수목록 가져오기
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 위치가 같는지
        return oldList[oldItemPosition] === newList[newItemPosition]

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]

    }

}