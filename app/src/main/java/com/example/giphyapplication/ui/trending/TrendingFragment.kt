package com.example.giphyapplication.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.giphyapplication.R
import com.example.giphyapplication.base.BaseFragment
import com.example.giphyapplication.databinding.FragmentTrendingBinding
import com.example.giphyapplication.utils.EventObserver
import com.example.giphyapplication.utils.UiState
import com.example.giphyapplication.utils.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrendingFragment : BaseFragment<FragmentTrendingBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTrendingBinding =
        FragmentTrendingBinding::inflate
    private lateinit var trendingPagingAdapter: TrendingPagingAdapter
    private val trendingViewModel: TrendingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        getTrending()
        observeFavoriteState()
        navigateToSearch()
        navigateToFavorite()
    }

    private fun setAdapter() {
        trendingPagingAdapter =
            TrendingPagingAdapter { favoriteItem -> trendingViewModel.onClickFavorite(favoriteItem) }

        binding.rvTrending.apply {
            setHasFixedSize(true)
            adapter = trendingPagingAdapter.withLoadStateFooter(
                footer = TrendingLoadStateAdapter { trendingPagingAdapter.retry() }
            )
        }

        trendingPagingAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                LoadState.Loading -> {
                    showLoadingView()
                    hideRecyclerView()
                }
                is LoadState.NotLoading -> {
                    hideLoadingView()
                    showRecyclerView()
                }
                is LoadState.Error -> {
                    hideLoadingView()
                    showErrorText()
                }
            }
        }
    }

    private fun getTrending() {
        viewLifecycleOwner.lifecycleScope.launch {
            trendingViewModel.trendingPagingFlow.collectLatest { trendingList ->
                trendingPagingAdapter.submitData(trendingList)
            }
        }
    }

    private fun observeFavoriteState() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(trendingViewModel) {
                uiState.collect { favoriteState ->
                    when (favoriteState) {
                        is UiState.AddToFavorites -> {
                            showAddSnackBar.observe(viewLifecycleOwner, EventObserver {
                                requireView().showSnackBar(R.string.favorites_add)
                            })
                        }
                        is UiState.RemoveFromFavorites -> {
                            showRemoveSnackBar.observe(viewLifecycleOwner, EventObserver {
                                requireView().showSnackBar(R.string.favorites_remove)
                            })
                        }
                    }
                }
            }
        }
    }

    private fun navigateToSearch() {
        binding.btnSearch.setOnClickListener { findNavController().navigate(R.id.action_trendingFragment_to_searchFragment) }
    }

    private fun navigateToFavorite() {
        binding.imgFavorite.setOnClickListener { findNavController().navigate(R.id.action_trendingFragment_to_favoriteFragment) }
    }

    private fun showLoadingView() {
        binding.loadingView.isVisible = true
    }

    private fun hideLoadingView() {
        binding.loadingView.isVisible = false
    }

    private fun showRecyclerView() {
        binding.rvTrending.isVisible = true
    }

    private fun hideRecyclerView() {
        binding.rvTrending.isVisible = false
    }

    private fun showErrorText() {
        binding.txtErrorMsg.isVisible = true
    }

    override fun onStop() {
        super.onStop()
        getTrending()
    }
}