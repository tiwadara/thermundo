package com.tiwa.thermondo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.tiwa.common.DummyData
import com.tiwa.common.DummyData.apiResponseObj
import com.tiwa.thermondo.ui.home.HomeState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShortLinkRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var shortLinkRepository: com.tiwa.thermondo.data.repository.MarsImagesRepository
    private lateinit var shortLinkService: com.tiwa.thermondo.data.api.NasaService
    private lateinit var shortLinkDao: com.tiwa.thermondo.data.dao.ShortLinkDao
    var testData = DummyData

    @Before
    fun setUp() {
        //Mocking shortLinkDao
        shortLinkDao = mock()
        whenever(shortLinkDao.getAllShortLinks()).thenReturn( flow { emit(testData.shortLinkList) })

        //Mocking ahortLinkService
        shortLinkService = mock()
        shortLinkRepository = com.tiwa.thermondo.data.repository.DefaultMarsImageRepository(
            shortLinkDao,
            shortLinkService
        )

    }

    @Test
    fun getNewShortLink_returns_Success() {
        runBlockingTest {
            whenever(shortLinkService.getMarsImages(testData.url)).thenReturn(apiResponseObj)
            val result = shortLinkRepository.getNewShortLink(testData.url)
                assertEquals(result.first() , HomeState.Loading )
                assertEquals(result.count() , 2 )
            }
    }
}