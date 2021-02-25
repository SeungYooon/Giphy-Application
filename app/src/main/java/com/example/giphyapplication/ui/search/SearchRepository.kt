package com.example.giphyapplication.ui.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.data.network.GiphyService
import com.example.giphyapplication.utils.Constant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val service: GiphyService) {
    fun loadBySearchTrending(query: String): Flow<PagingData<FavoriteItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(service, query) }
        ).flow
    }
}