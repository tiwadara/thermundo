package com.tiwa.thermondo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tiwa.common.CoroutineTestRule
import com.tiwa.thermondo.data.repository.MarsImagesRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel

    private var repository: MarsImagesRepository = mock()

    private var observer: Observer<HomeState<*>> = mock()

    @Before
    fun setup() {

        viewModel = HomeViewModel(repository = repository)
    }

    @Test
    fun givenStateFlow_whenStateChanges_shouldReturnState() {
        viewModel.state.asLiveData().observeForever(observer)
        verify(observer).onChanged(HomeState.Loading)
        viewModel.state.compareAndSet(HomeState.Loading, HomeState.ImagesReturned(mutableListOf()))
        verify(observer).onChanged(HomeState.ImagesReturned(mutableListOf()))
    }

    @Test
    fun givenMessageViewModel_whenAnEventIsCalled_shouldReturnState() = runBlocking {
        viewModel.state.asLiveData().observeForever(observer)
        `when`(repository.getMarsImages()).thenReturn(flow {
            emit(HomeState.Loading)
            emit(HomeState.ImagesReturned(mutableListOf()))
        })
        val response = repository.getMarsImages().toList()
        verify(repository, Mockito.times(1)).getMarsImages()
        assertEquals(response.first(), HomeState.Loading)
        assertEquals(response.size, 2)
        assertEquals(response.drop(1).first(), HomeState.ImagesReturned(mutableListOf()))
    }

}