package com.example.foody.di

import com.example.foody.Constrants.Companion.BASE_URL
import com.example.foody.FoodRecipesApi
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
@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {

    fun provideHttpClient() : OkHttpClient{

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
        return Retrofit.Builder()
            .baseUrl(BASE_URL) //다른 Class 에 있는 변수값을 가져다 쓸 수 있나 ??
            .client(okHttpClient)
                //json 변화기 Factory
            .addConverterFactory(gsonConverterFactory)
            .build()

    }

    // 주입 할 객체
    // Provides constructor 호출 (의존성 생성)
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}