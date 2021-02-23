package com.example.giphyapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.giphyapplication.data.db.entity.FavoriteItem


class DiffCallback : DiffUtil.ItemCallback<FavoriteItem>() {
    override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean =
        oldItem == newItem
}
