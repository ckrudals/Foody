package com.example.foody.util

import androidx.recyclerview.widget.DiffUtil

// getOldListSize : 현재 리스트에 노출하고 있는 List size
//getNewListSize : 새로 추가하거나, 갱신해야 할 List size
//areItemsTheSame : 현재 리스트에 노출하고 있는 아이템과 새로운 아이템이 서로 같은지 비교한다. 보통 고유한 ID 값을 체크한다.
//areContentsTheSame : 현재 리스트에 노출하고 있는 아이템과 새로운 아이템의 equals 를 비교한다.

class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
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