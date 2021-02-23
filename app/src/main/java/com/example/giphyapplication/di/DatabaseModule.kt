package com.example.giphyapplication.di

import android.content.Context
import com.example.giphyapplication.data.db.GiphyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context): GiphyDatabase =
        GiphyDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideMyDao(database: GiphyDatabase) = database.favoriteDao()
}