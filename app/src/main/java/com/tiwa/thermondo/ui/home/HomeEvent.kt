package com.tiwa.thermondo.ui.home


sealed class HomeEvent {
    object GetMarsImagesEvent : HomeEvent()
    object ClearSearch : HomeEvent()
    data class  SearchImagesEvent(val query: String) : HomeEvent()
}
