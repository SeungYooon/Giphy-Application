package com.example.giphyapplication.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapplication.R
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.databinding.ItemFavoriteBinding
import com.example.giphyapplication.utils.DiffCallback
import com.example.giphyapplication.utils.extension.bindImage

class FavoriteAdapter :
    ListAdapter<FavoriteItem, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteItem) {
            binding.apply {
                bindImage(imgFavorite, item.images.original.imageUrl)
                txtTitleContent.text = item.title
                if (item.name.isEmpty()) txtUserName.setText(R.string.no_username)
                else txtUserName.text = item.name
                txtTrendingTime.text = item.trendingTime.replaceAfter(" ", "")
            }
        }
    }
}