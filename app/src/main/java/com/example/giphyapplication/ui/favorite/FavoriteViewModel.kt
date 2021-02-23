package com.example.giphyapplication.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphyapplication.ui.trending.TrendingRepository
import com.example.giphyapplication.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(private val trendingRepository: TrendingRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getFavoriteList()
    }

    private fun getFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            trendingRepository.getAllFavorites()
                .onStart {
                    _uiState.value = UiState.Loading
                    delay(300)
                }
                .catch { exception -> UiState.Error(exception.localizedMessage) }
                .collect { favoriteList ->
                    if (favoriteList.isEmpty()) _uiState.value = UiState.Empty
                    else _uiState.value = UiState.Success(favoriteList)
                }
        }
    }
}