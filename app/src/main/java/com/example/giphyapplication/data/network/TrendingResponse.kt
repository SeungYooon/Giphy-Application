package com.example.giphyapplication.data.network

import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    @SerializedName("data")
    val `data`: List<FavoriteItem>
)
