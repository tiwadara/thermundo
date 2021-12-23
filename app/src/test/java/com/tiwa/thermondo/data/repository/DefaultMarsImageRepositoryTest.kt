package com.tiwa.thermondo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.tiwa.common.DummyData
import com.tiwa.thermondo.data.api.NasaService
import com.tiwa.thermondo.ui.home.HomeState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultMarsImageRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: MarsImagesRepository
    private var service: NasaService = mock()
    private var dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        repository = DefaultMarsImageRepository(dispatcher, service)
    }

    @Test
    fun `when getMarsImages() is called then a list of photo is returned`() =
        dispatcher.runBlockingTest {
            whenever(service.getMarsImages()).thenReturn(DummyData.apiResponseObj)
            val result = repository.getMarsImages()
            Assert.assertEquals(result.first(), HomeState.Loading)
            Assert.assertEquals(result.count(), 2)
        }
}