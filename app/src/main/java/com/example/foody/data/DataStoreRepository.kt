package com.example.foody.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foody.util.Constrants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.util.Constrants.Companion.DEFAULT_MEAL_TYPE
import com.example.foody.util.Constrants.Companion.PREFERENCES_BACK_ONLINE
import com.example.foody.util.Constrants.Companion.PREFERENCES_DIET_TYPE
import com.example.foody.util.Constrants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foody.util.Constrants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foody.util.Constrants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foody.util.Constrants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


// background thread

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    /***
    간단한 데이터 저장할 때 씀
    파일 형태로 저장


     */
    private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }


    // context.createDataStore 사라지고 globalDataStore 로 마이그레이션\
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline]=backOnline
        }
    }

    // 데이터 저장
    suspend fun saveMainAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {

        Log.d("TAG", "saveMainAndDietType: ")
        // 데이터 스트림에 내보낼 수 있는 새 흐름을 만듦
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }

    }

    //dataStore은 sharePrefernce와 다르게 코루틴이 가능하다
    // flow로 감싼다. (비동기 처리)
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is Exception) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId
            )
        }

    val readBackOnline:Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is Exception) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }   .map { preferences ->
            val backOnline= preferences[PreferenceKeys.backOnline] ?: false // 바로 초기화
            backOnline
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)