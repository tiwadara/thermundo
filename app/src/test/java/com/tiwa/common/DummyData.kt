package com.tiwa.common

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tiwa.thermondo.data.model.ApiResponse
import com.tiwa.thermondo.data.model.Photo

@Suppress("UNCHECKED_CAST")
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {

    val apiResponseObj: ApiResponse = Gson().fromJson(
        DependencyProvider.getResponseFromJson("response"),
        object : TypeToken<ApiResponse>(){}.type) as ApiResponse

}
