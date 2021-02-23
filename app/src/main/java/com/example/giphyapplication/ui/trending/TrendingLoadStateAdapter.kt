package com.example.giphyapplication.ui.trending

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class TrendingLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<TrendingStateViewHolder>() {
    override fun onBindViewHolder(holder: TrendingStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): TrendingStateViewHolder {
        return TrendingStateViewHolder.create(parent, retry)
    }
}