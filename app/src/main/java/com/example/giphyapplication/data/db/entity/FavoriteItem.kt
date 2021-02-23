package com.example.giphyapplication.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "favorite_table")

data class FavoriteItem(
    @PrimaryKey
    @SerializedName("title")
    val title: String,
    val id: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("trending_datetime")
    val trendingTime: String,
    @Embedded
    var images: Images,
    var isChecked: Boolean = false
)

data class Images(
    @Embedded
    val original: Original
)

data class Original(
    @SerializedName("url")
    val imageUrl: String
)
