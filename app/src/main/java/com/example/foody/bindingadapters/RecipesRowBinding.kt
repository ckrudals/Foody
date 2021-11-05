package com.example.foody.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foody.R
import com.example.foody.models.Result
import com.example.foody.ui.fragment.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesRowBinding {
    /***
    BindingAdapter을 사용하는 이유
    dataBinding은 xml에서 바인딩 시켜준다.
    이미지 같은 부분은 Int 형이기 때문에 오류가 난다.
    이걸 해결해 주려면 BindingAdapter을 써서 내부 로직을 작성한다.
     ***/

    /***
    BindingAdapter은 메모리 상에 만들고 사용하기 때문에 static으로 만들어 사용해야 하고
    Kotlin에서는 Java와 다르게 Object로 설정한다.
    JvmStatic 은 Static 함수로 설정해주기 위한 어노테이션(Annotation) 이다.
     ***/

    // 정적변수는 초기화 하지 않아도 자동으로 0이 저장됨
    // 정적 지역 변수의 메모리 생성 시점 중괄호 내에서 초기화 할때

    companion object {
        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout:ConstraintLayout, result:Result){


            recipeRowLayout.setOnClickListener {
                try{
                    val action=
                        RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e:Exception){
                    Log.d("TAG", "onRecipeClickListener: ")
                }
            }
        }
     @BindingAdapter("loadImageFromUrl")
     @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {

            //coil 라이브러리
            imageView.load(imageUrl){
                //
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinuztes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {

            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        //이미지뷰에 색깔 넣기
                        // setColorFilter와 Tint 값이 있다.
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description:String?){
            if(description!=null){
                val desc= Jsoup.parse(description).text()
                textView.text=desc
            }
        }
    }
}