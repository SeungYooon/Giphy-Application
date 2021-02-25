package com.example.giphyapplication.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.giphyapplication.data.db.entity.FavoriteItem
import kotlinx.coroutines.flow.Flow

class SearchViewModel @ViewModelInject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var queryValue: String? = null
    private var currentSearchResult: Flow<PagingData<FavoriteItem>>? = null

    fun loadBySearchTrending(query: String): Flow<PagingData<FavoriteItem>> {
        val lastResult = currentSearchResult
        if (query == queryValue && lastResult != null) {
            return lastResult
        }
        queryValue = query
        val newResult: Flow<PagingData<FavoriteItem>> =
            searchRepository.loadBySearchTrending(query).cachedIn(viewModelScope)

        currentSearchResult = newResult
        return newResult
    }
}