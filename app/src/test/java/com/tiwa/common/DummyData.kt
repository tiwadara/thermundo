package com.tiwa.common

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tiwa.thermondo.data.model.ApiResponse
import com.tiwa.thermondo.data.model.Photo

@Suppress("UNCHECKED_CAST")
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {
    
    const val url = "test.com"

    val apiResponseObj = ApiResponse(mutableListOf())

    val photoList: List<Photo> = Gson().fromJson(
        DependencyProvider.getResponseFromJson("response-list"),
        object : TypeToken<List<Photo>>(){}.type) as List<Photo>

}
