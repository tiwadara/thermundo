package com.tiwa.thermondo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiwa.thermondo.data.repository.DefaultMarsImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DefaultMarsImageRepository) :
    ViewModel() {

    private val eventChannel = Channel<HomeEvent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<HomeState<Any>>(HomeState.Loading)
    val state: MutableStateFlow<HomeState<Any>>
        get() = _state

    init {
        viewModelScope.launch {
            handleEvents()
        }
    }

    private suspend fun handleEvents() {
        eventChannel.consumeAsFlow().collectLatest { event ->
            when (event) {
                is HomeEvent.GetMarsImagesEvent -> {
                    repository.getMarsImages().onEach { state ->
                        _state.value = state
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun getMarsImages() {
        eventChannel.offer(HomeEvent.GetMarsImagesEvent)
    }
}