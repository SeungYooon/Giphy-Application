package com.example.giphyapplication.ui.trending

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.giphyapplication.data.db.dao.FavoriteDao
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.utils.Constant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepository @Inject constructor(
    private val trendingPagingSource: TrendingPagingSource,
    private val favoriteDao: FavoriteDao
) {
    fun loadTrending(): Flow<PagingData<FavoriteItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { trendingPagingSource }
        ).flow
    }

    fun loadAllTrending() = trendingPagingSource.loadAllTrending()

    fun getAllFavorites(): Flow<List<FavoriteItem>> = favoriteDao.getAllFavorites()

    suspend fun insertToFavorites(favoriteItem: FavoriteItem) =
        favoriteDao.insertToFavorites(favoriteItem)

    suspend fun removeFromFavorites(favoriteItem: FavoriteItem) =
        favoriteDao.removeFromFavorites(favoriteItem)

    fun getTrendingByName(userName: String) = favoriteDao.getTrendingByName(userName)
}