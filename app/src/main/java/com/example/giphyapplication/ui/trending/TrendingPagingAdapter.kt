package com.example.giphyapplication.ui.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapplication.R
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.databinding.ItemTrendingBinding
import com.example.giphyapplication.utils.DiffCallback
import com.example.giphyapplication.utils.extension.bindImage

class TrendingPagingAdapter(
    private val onCheck: (FavoriteItem) -> Unit
) : PagingDataAdapter<FavoriteItem, TrendingPagingAdapter.TrendingViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val data = getItem(position)
        val favoriteBtn = holder.itemView.findViewById<ToggleButton>(R.id.toggleFavorite)
        holder.setIsRecyclable(false)

        if (data != null) {
            favoriteBtn.isChecked = data.isChecked
        }

        favoriteBtn.setOnCheckedChangeListener(null)
        favoriteBtn.setOnCheckedChangeListener { _, isChecked ->
            getItem(holder.absoluteAdapterPosition)?.isChecked = isChecked
        }

        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            ItemTrendingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class TrendingViewHolder(
        private val binding: ItemTrendingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteItem: FavoriteItem) {
            binding.apply {
                bindImage(imgGif, favoriteItem.images.original.imageUrl)

                toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) onCheck.invoke(favoriteItem)
                }
            }
        }
    }
}