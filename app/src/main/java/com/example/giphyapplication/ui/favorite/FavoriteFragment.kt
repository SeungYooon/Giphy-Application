package com.example.giphyapplication.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.giphyapplication.base.BaseFragment
import com.example.giphyapplication.databinding.FragmentFavoriteBinding
import com.example.giphyapplication.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding =
        FragmentFavoriteBinding::inflate

    lateinit var favoriteAdapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        showFavoriteList()
        setBackButton()
    }

    private fun setAdapter() {
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorite.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun showFavoriteList() {
        viewModel.uiState.asLiveData().observe(viewLifecycleOwner, Observer { favorite ->
            when (favorite) {
                is UiState.Loading -> {
                    showLoadingView()
                    hideRecyclerView()
                }
                is UiState.Empty -> {
                    hideLoadingView()
                    showEmptyText()
                }
                is UiState.Success -> {
                    hideLoadingView()
                    showRecyclerView()
                    favoriteAdapter.submitList(favorite.list)
                }
                is UiState.Error -> {
                    hideLoadingView()
                    showErrorText(favorite.message.toString())
                }
            }
        })
    }

    private fun setBackButton() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showLoadingView() {
        binding.loadingView.isVisible = true
    }

    private fun hideLoadingView() {
        binding.loadingView.isVisible = false
    }

    private fun showRecyclerView() {
        binding.rvFavorite.isVisible = true
    }

    private fun hideRecyclerView() {
        binding.rvFavorite.isVisible = false
    }

    private fun showEmptyText() {
        binding.txtEmpty.isVisible = true
    }

    private fun showErrorText(msg: String) {
        binding.txtError.text = msg
    }
}