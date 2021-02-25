package com.example.giphyapplication.data.db.dao

import androidx.room.*
import com.example.giphyapplication.data.db.entity.FavoriteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_table WHERE isChecked=:state")
    fun getAllFavorites(state: Boolean = true): Flow<List<FavoriteItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToFavorites(favoriteItem: FavoriteItem)

    @Delete
    suspend fun removeFromFavorites(favoriteItem: FavoriteItem)
}