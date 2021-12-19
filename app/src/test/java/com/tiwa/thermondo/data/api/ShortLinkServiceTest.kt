package com.tiwa.thermondo.data.api

import com.tiwa.common.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import java.io.IOException

@ExperimentalCoroutinesApi
class ShortLinkServiceTest {

    private lateinit var shortLinkService: NasaService
    private lateinit var mockWebServer: MockWebServer
    private val testData = DummyData

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        shortLinkService = DependencyProvider.getRetrofit(mockWebServer.url("/"))
            .create(com.tiwa.thermondo.data.api.NasaService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getNewSHortLink_returns200() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("response"))
        }
        runBlocking {
            shortLinkService.getMarsImages(testData.url).let {
                Assert.assertNotNull(it.result)
            }
        }
    }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}