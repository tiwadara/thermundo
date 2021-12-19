package com.tiwa.thermondo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiwa.thermondo.databinding.FragmentHomeBinding
import com.tiwa.thermondo.extensions.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: MarsImageListAdapter

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
        initializeAdapter()
    }

    private fun initializeAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MarsImageListAdapter(
            { position, id ->
                val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                findNavController().navigate(directions)
            }, requireContext()
        )
        binding.recyclerView.adapter = adapter
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
                adapter.submitList(state.data)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}