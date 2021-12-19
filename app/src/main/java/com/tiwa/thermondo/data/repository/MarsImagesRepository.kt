package com.tiwa.thermondo.data.repository

import com.tiwa.thermondo.ui.home.HomeState
import kotlinx.coroutines.flow.Flow

interface MarsImagesRepository {
    suspend fun getMarsImages(): Flow<HomeState<Any>>
}