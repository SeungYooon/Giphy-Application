package com.example.giphyapplication.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {
    @GET("trending")
    suspend fun loadTrending(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): TrendingResponse
}