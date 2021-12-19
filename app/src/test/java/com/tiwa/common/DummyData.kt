package com.tiwa.common

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Suppress("UNCHECKED_CAST")
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {
    
    const val url = "test.com"

    val apiResponseObj = com.tiwa.thermondo.data.model.ApiResponse(
        true,
        com.tiwa.thermondo.data.model.ShortLinkData(0, "", "", "", "", "", "", "", "")
    )

    val shortLinkList: List<com.tiwa.thermondo.data.model.ShortLinkData> = Gson().fromJson( DependencyProvider.getResponseFromJson("response-list"), object : TypeToken<List<com.tiwa.thermondo.data.model.ShortLinkData>>(){}.type) as List<com.tiwa.thermondo.data.model.ShortLinkData>

}
