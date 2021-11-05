package com.example.foody.di

import android.content.ContentValues.TAG
import android.util.Log
import com.example.foody.util.Constrants.Companion.BASE_URL
import com.example.foody.data.network.FoodRecipesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


// Dagger Hilt 에서는 표준적으로 제공하는 component 들이 이미 존재하기 때문에
// InstallIn 어노테이션을 사용해서 , 표준 component 에 install 할수 있다

// SingletonComponent 
// 싱글톤으로 돌아가는 Component 말 그대로 인듯

// component 는 연결된 Module 로 의존성을 생성하고
// Inject 에 요청받은 인스턴스에 생성된 객체를 주입한다.

//Module, InstallIn 은 constructor 호출하는 모듈 생성(선언)

// 모듈이 의존성 ..??
// 인스턴스를 하나만 받아옴 << 싱글톤
// 네트워크 Retrofit2에서 객체를 받아올 때 하나씩만 안받아오면 메모리 과부하 걸림
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient{
        Log.d(TAG, "provideHttpClient: ")
        return OkHttpClient.Builder()
         //제한시간 15초
            .readTimeout(15, TimeUnit.SECONDS)
            //연결시간 15초
            .connectTimeout(15,TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory{
        Log.d(TAG, "provideConverterFactory: ")
        return GsonConverterFactory.create()
    }

    // Provides 로 객체를 생성
    // Singleton 으로 어디서나 동일한 객체 제공
        @Singleton
        @Provides
        fun provideRetrofitInstance(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            Log.d(TAG, "provideRetrofitInstance: ")
            return Retrofit.Builder()
                .baseUrl(BASE_URL) //다른 Class 에 있는 변수값을 가져다 쓸 수 있나 ??
                .client(okHttpClient)
                    //json 변화기 Factory
                .addConverterFactory(gsonConverterFactory)
                .build()

        }

    // 주입 할 객체
    // Provides constructor 호출 (의존성 생성)
    // Singleton 하나의 인스턴스만 가짐
    @Singleton
    @Provides
    //data source 에 삽입
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        Log.d(TAG, "provideApiService: ")
        return retrofit.create(FoodRecipesApi::class.java)
    }
}