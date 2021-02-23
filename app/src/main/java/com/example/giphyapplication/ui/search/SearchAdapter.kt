package com.example.giphyapplication.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.databinding.ItemSearchBinding
import com.example.giphyapplication.utils.DiffCallback
import com.example.giphyapplication.utils.extension.bindImage

class SearchAdapter :
    ListAdapter<FavoriteItem, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
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