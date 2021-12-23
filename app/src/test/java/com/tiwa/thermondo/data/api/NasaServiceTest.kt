package com.tiwa.thermondo.data.api

import com.tiwa.common.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import java.io.IOException

@ExperimentalCoroutinesApi
class NasaServiceTest {

    private lateinit var service: NasaService
    private lateinit var mockWebServer: MockWebServer
    private val testData = DummyData

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        service = DependencyProvider.getRetrofit(mockWebServer.url("/"))
            .create(NasaService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMarsImages_returns200() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("response"))
        }
        runBlocking {
            service.getMarsImages().let {
                Assert.assertNotNull(it.photos)
            }
        }
    }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}