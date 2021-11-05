package com.example.foody.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.foody.data.Repository
import com.example.foody.data.database.RecipesEntity
import com.example.foody.models.FoodRecipe
import com.example.foody.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

// ViewModelInject 는 삭제됨 HiltViewModel 로 쓰기
@HiltViewModel
// 생성자 주입
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    // 오류 생기는 이유
    // suspend 를 안붙여서

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()

        //인터넷이 연결되었을 때
        if (hasInternetConnection()) {
            try {
                Log.d(TAG, "searchRecipesSafeCall: ")
                val response = repository.remote.searchRecipes(searchQuery)

                searchRecipesResponse.value = handleFoodRecipeResponse(response)
                Log.d(TAG, "searchRecipesSafeCall value: ${searchRecipesResponse.value} ")

            } catch (e: Exception) {
                searchRecipesResponse.value = NetworkResult.Error("Recipes not found")
            }
        } else {
            // 인터넷 연결 실패
            searchRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    //LiveData 선언

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {

        Log.d(TAG, "MainViewModel - getRecipes() called")
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {

        Log.d(TAG, "getRecipesSafeCall: ")
        // 먼저 Loading 으로
        recipesResponse.value = NetworkResult.Loading()

        //인터넷이 연결되었을 때
        if (hasInternetConnection()) {
            try {
                Log.d(TAG, "getRecipesSafeCall: ")
                val response = repository.remote.getRecipes(queries)

                recipesResponse.value = handleFoodRecipeResponse(response)
                Log.d(TAG, "getRecipesSafeCall value: ${recipesResponse.value} ")

                val foodRecipe = recipesResponse.value!!.data
                Log.d(TAG, "getRecipesSafeCall: $foodRecipe")
                if (foodRecipe != null) {
                    Log.d(TAG, "foodRecipe: ")
                    offlineCashRecipes(foodRecipe)
                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found")
            }
        } else {
            // 인터넷 연결 실패
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCashRecipes(foodRecipe: FoodRecipe) {

        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)

    }

    private fun handleFoodRecipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {

            //너무 오래 걸릴 때
            response.message().toString().contains("Timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            // 402가 뜰 때
            response.code() == 402 -> {
                Log.d(TAG, "handleFoodRecipeResponse: ${response.body()}")
                return NetworkResult.Error("API Key Limited.")
            }

            //데이터가 없을 때
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }

            //성공했을 때 data 를 가져옴
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }


    //인터넷 확인 함수

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        //연결 관리자
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}

