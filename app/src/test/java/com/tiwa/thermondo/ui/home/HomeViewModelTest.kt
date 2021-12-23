package com.tiwa.thermondo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import app.cash.turbine.test
import com.google.common.truth.ExpectFailure.assertThat
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tiwa.common.CoroutineTestRule
import com.tiwa.common.DummyData
import com.tiwa.thermondo.data.repository.MarsImagesRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel

    private var repository: MarsImagesRepository = mock()

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository = repository)
    }

    @Test
    fun givenHomeViewModel_whenAnEventIsCalled_shouldReturnState() = coroutineRule.runBlockingTest {
        `when`(repository.getMarsImages()).thenReturn(flowOf(HomeState.ImagesReturned(DummyData.apiResponseObj.photos)))

        viewModel.state.test {
            viewModel.getMarsImages()
            Truth.assertThat(awaitItem()).isEqualTo(HomeState.Loading)
            Truth.assertThat(awaitItem()).isEqualTo(HomeState.ImagesReturned(DummyData.apiResponseObj.photos))
            cancelAndConsumeRemainingEvents()
        }

        verify(repository, Mockito.times(1)).getMarsImages()
    }

}