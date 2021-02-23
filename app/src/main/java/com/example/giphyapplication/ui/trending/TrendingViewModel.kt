package com.example.giphyapplication.ui.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.utils.EventWrapper
import com.example.giphyapplication.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class TrendingViewModel @ViewModelInject constructor(
    private val trendingRepository: TrendingRepository
) : ViewModel() {
    private val _showAddSnackBar = MutableLiveData<EventWrapper<Boolean>>()
    val showAddSnackBar: LiveData<EventWrapper<Boolean>> = _showAddSnackBar

    private val _showRemoveSnackBar = MutableLiveData<EventWrapper<Boolean>>()
    val showRemoveSnackBar: LiveData<EventWrapper<Boolean>> = _showRemoveSnackBar

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    val trendingPagingFlow: Flow<PagingData<FavoriteItem>> =
        trendingRepository.loadTrending().distinctUntilChanged().cachedIn(viewModelScope)

    fun onClickFavorite(favoriteItem: FavoriteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!favoriteItem.isChecked) {
                favoriteItem.isChecked = true
                trendingRepository.insertToFavorites(favoriteItem)
                _uiState.value = UiState.AddToFavorites(favoriteItem)
                _showAddSnackBar.postValue(EventWrapper(true))
            } else {
                trendingRepository.removeFromFavorites(favoriteItem)
                favoriteItem.isChecked = false
                _uiState.value = UiState.RemoveFromFavorites(favoriteItem)
                _showRemoveSnackBar.postValue(EventWrapper(true))
            }
        }
    }
}