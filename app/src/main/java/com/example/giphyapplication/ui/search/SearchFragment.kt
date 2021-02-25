package com.example.giphyapplication.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.giphyapplication.base.BaseFragment
import com.example.giphyapplication.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearch()
        setAdapter()
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

    private fun initSearch() {
        binding.searchView.setQuery("", false)
        showEmptyText()
    }

    private fun setUpSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchList -> searchTrending(searchList) }
                return true
            }
        })
    }

    private fun searchTrending(query: String) {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadBySearchTrending(query).collectLatest { searchList ->
                if (query.isEmpty()) {
                    showEmptyText()
                    hideRecyclerView()
                } else {
                    hideEmptyText()
                    showRecyclerView()
                    searchAdapter.submitData(searchList)
                }
            }
        }
    }

    private fun setCloseButton() {
        binding.imgClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showRecyclerView() {
        binding.rvSearch.isVisible = true
    }

    private fun hideRecyclerView() {
        binding.rvSearch.isVisible = false
    }

    private fun showEmptyText() {
        binding.txtEmptyMsg.isVisible = true
    }

    private fun hideEmptyText() {
        binding.txtEmptyMsg.isVisible = false
    }
}