package com.tiwa.thermondo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.tiwa.thermondo.databinding.FragmentDetailBinding
import com.tiwa.thermondo.extensions.loadImage
import com.tiwa.thermondo.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val argument: DetailFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photo = viewModel.unfilteredList.find { it.id == argument.position }
        binding.imageView2.loadImage(photo?.img_src)
        binding.textView.text = photo?.earth_date
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}