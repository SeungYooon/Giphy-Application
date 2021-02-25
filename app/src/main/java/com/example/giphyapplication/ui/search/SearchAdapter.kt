package com.example.giphyapplication.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.databinding.ItemSearchBinding
import com.example.giphyapplication.utils.DiffCallback
import com.example.giphyapplication.utils.extension.bindImage

class SearchAdapter :
    PagingDataAdapter<FavoriteItem, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteItem) {
            binding.apply {
                bindImage(imgGif, item.images.original.imageUrl)
            }
        }
    }
}