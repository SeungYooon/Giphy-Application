package com.example.giphyapplication.utils

import com.example.giphyapplication.data.db.entity.FavoriteItem

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(val list: List<FavoriteItem>) : UiState()
    data class Error(val message: String?) : UiState()
    data class AddToFavorites(val data: FavoriteItem) : UiState()
    data class RemoveFromFavorites(val data: FavoriteItem) : UiState()
}