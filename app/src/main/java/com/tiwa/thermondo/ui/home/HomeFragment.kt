package com.tiwa.thermondo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiwa.thermondo.databinding.FragmentHomeBinding
import com.tiwa.thermondo.extensions.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var marsImageListAdapter: MarsImageListAdapter
    var queryTextChangedJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeState()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(this)
        initializeAdapter()
    }

    private fun initializeAdapter() {

        marsImageListAdapter = MarsImageListAdapter { _, id ->
            val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
            findNavController().navigate(directions)
        }
        with(binding.recyclerView){
            hasFixedSize()
            layoutManager =  LinearLayoutManager(requireContext())
            adapter = marsImageListAdapter
        }
    }

    private fun observeState() {
        viewModel.state.asLiveData().observe(viewLifecycleOwner, {
            handleState(it)
        })
        viewModel.getMarsImages()
    }

    /**
     * This is the method for observing state changes based on User Interactions
     *
     * @param state Toggle the UI based on values received from the
     * sealed class com.tiwa.shortlyapp.data.state.ShortLinkState
     */

    private fun handleState(state: HomeState<Any>?) {
        when (state) {
            HomeState.Loading -> {
                toggleLoader(true)
            }
            is HomeState.Failed<*> -> {
                toggleLoader(false)
                toastMessage(state.data)
            }
            is HomeState.ImagesReturned -> {
                toggleLoader(false)
                viewModel.unfilteredList = state.data
                marsImageListAdapter.submitList(state.data)
            }
            HomeState.ClearSearch -> {
                marsImageListAdapter.submitList(viewModel.unfilteredList)
            }
            is HomeState.SearchedImagesReturned -> {
                marsImageListAdapter.submitList(state.data)
            }
        }
    }

    /**
     * @param message is passed to show a toast message onn the UI
     */

    private fun toastMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    /**
     * This show a horizontal loader at the bottom
     * @param show is passed to enable or disable visibility
     */
    private fun toggleLoader(isLoading: Boolean) {
        binding.progressBar.showIf(isLoading)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.searchQuery = query
        binding.searchView.clearFocus()
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.searchQuery = newText
        if (newText.isBlank()) {
            viewModel.clearSearch()
            return true
        }
        queryTextChangedJob?.cancel()
        queryTextChangedJob = CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            viewModel.search(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}