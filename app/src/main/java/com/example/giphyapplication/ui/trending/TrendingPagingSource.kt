package com.example.giphyapplication.ui.trending

import androidx.paging.PagingSource
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.data.network.GiphyService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingPagingSource @Inject constructor(private val giphyService: GiphyService) :
    PagingSource<Int, FavoriteItem>() {

    fun loadAllTrending() = flow { emit(giphyService.loadTrending(100, 0)) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteItem> {
        return try {
            val nextPage = params.key ?: 1
            val response = giphyService.loadTrending(nextPage)
            val trendingList = response.data
            LoadResult.Page(
                data = trendingList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (trendingList.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}