package com.tiwa.thermondo.ui.home

import com.tiwa.thermondo.data.model.Photo

sealed class HomeState<out R> {
    object Loading : HomeState<Nothing>()
    object ClearSearch : HomeState<Nothing>()
    data class SearchedImagesReturned(val data: List<Photo>) : HomeState<List<Photo>>()
    data class ImagesReturned(val data: List<Photo>) : HomeState<List<Photo>>()
    data class Failed<out T>(val data: String?) : HomeState<Nothing>()
}