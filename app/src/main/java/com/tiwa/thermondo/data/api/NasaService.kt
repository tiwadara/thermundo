package com.tiwa.thermondo.data.api

import com.tiwa.thermondo.data.model.ApiResponse
import retrofit2.http.GET

interface NasaService {

    @GET("curiosity/photos?sol=10&api_key=DEMO_KEY")
    suspend fun getMarsImages(): ApiResponse

}