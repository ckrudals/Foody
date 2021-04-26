package com.example.foody.di

import android.content.Context
import androidx.room.Room
import com.example.foody.data.database.RecipesDatabase
import com.example.foody.util.Constrants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

//이해못함 왜 필요 ?
@Module
@InstallIn(ApplicationContext::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME

    ).build()


    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}