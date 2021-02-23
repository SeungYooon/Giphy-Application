package com.example.giphyapplication.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.giphyapplication.ui.trending.TrendingRepository
import com.example.giphyapplication.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            trendingRepository.loadAllTrending()
                .onStart { _uiState.value = UiState.Loading }
                .catch { exception -> _uiState.value = UiState.Error(exception.localizedMessage) }
                .collect { trendingList -> _uiState.value = UiState.Success(trendingList.data) }
        }
    }

    private val _searchQuery = MutableStateFlow("")

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    val searchResult = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { userName ->
            trendingRepository.getTrendingByName(userName)
        }
        .flowOn(Dispatchers.IO)
        .catch { exception -> Throwable(exception) }
        .asLiveData(viewModelScope.coroutineContext)
}