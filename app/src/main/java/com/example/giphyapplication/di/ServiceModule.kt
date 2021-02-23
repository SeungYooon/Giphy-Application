package com.example.giphyapplication.di

import com.example.giphyapplication.data.network.GiphyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): GiphyService =
        retrofit.create(GiphyService::class.java)
}