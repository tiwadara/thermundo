package com.tiwa.common

import android.annotation.SuppressLint
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okio.Okio
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

const val CONNECT_TIMEOUT = 10L
const val WRITE_TIMEOUT = 30L
const val READ_TIMEOUT = 10L

object DependencyProvider {

    /**
     * Returns a Retrofit instance for Testing
     */
    fun getRetrofit(baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(
                        OkHttpClient.Builder()
                                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).build()
                )
                .build()
    }

    /**
     *Helper to read a JSON file and return a JSON string
     *Note: JSON file should have the structure "module/resources/api-response/filename.json"
     * @param fileName: File's name
     * @return JSON String
     */
    @SuppressLint("NewApi")
    fun getResponseFromJson(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName.json")
        val source = Okio.buffer(Okio.source(inputStream!!))
        return source.readString(StandardCharsets.UTF_8)
    }
}