package com.example.giphyapplication.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.giphyapplication.base.BaseFragment
import com.example.giphyapplication.databinding.FragmentSearchBinding
import com.example.giphyapplication.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        observeTrending()
        setCloseButton()
        setUpSearch()
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()

        binding.rvSearch.apply {
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    private fun observeTrending() {
        viewModel.uiState.asLiveData().observe(viewLifecycleOwner, Observer { trendList ->
            when (trendList) {
                UiState.Loading -> {
                    showLoadingView()
                    hideRecyclerView()
                }
                is UiState.Success -> {
                    hideLoadingView()
                    showRecyclerView()
                    searchAdapter.submitList(trendList.list)
                }
                is UiState.Error -> {
                    hideLoadingView()
                    showErrorText()
                }
            }
        })
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setUpSearch() {
        binding.searchView.setQuery("", false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText.toString())
                return true
            }
        })

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { userName ->
            searchAdapter.submitList(userName)
        })
    }

    private fun setCloseButton() {
        binding.imgClose.setOnClickListener {
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
        binding.rvSearch.isVisible = true
    }

    private fun hideRecyclerView() {
        binding.rvSearch.isVisible = false
    }

    private fun showErrorText() {
        binding.txtErrorMsg.isVisible = true
    }
}