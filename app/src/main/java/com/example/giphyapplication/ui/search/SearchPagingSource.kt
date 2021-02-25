package com.example.giphyapplication.ui.search

import androidx.paging.PagingSource
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.data.network.GiphyService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val service: GiphyService,
    private val query: String
) : PagingSource<Int, FavoriteItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteItem> {
        return try {
            val nextPage = params.key ?: 1
            val response = service.searchTrending(query, 0)
            val searchList = response.data
            LoadResult.Page(
                data = searchList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (searchList.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}