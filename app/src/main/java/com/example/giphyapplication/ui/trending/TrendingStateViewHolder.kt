package com.example.giphyapplication.ui.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapplication.R
import com.example.giphyapplication.databinding.LoadStateFooterItemBinding

class TrendingStateViewHolder(
    private val binding: LoadStateFooterItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            errorMsg.isVisible = loadState !is LoadState.Loading
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): TrendingStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_item, parent, false)
            val binding = LoadStateFooterItemBinding.bind(view)
            return TrendingStateViewHolder(binding, retry)
        }
    }
}
