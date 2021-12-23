package com.tiwa.thermondo.data.repository

import com.tiwa.thermondo.data.api.NasaService
import com.tiwa.thermondo.di.IoDispatcher
import com.tiwa.thermondo.ui.home.HomeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DefaultMarsImageRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val nasaService: NasaService,
) : MarsImagesRepository {

    override suspend fun getMarsImages(): Flow<HomeState<Any>> = flow {
        emit(HomeState.Loading)
        try {
            val response = nasaService.getMarsImages()
            emit(HomeState.ImagesReturned(response.photos))
        } catch (e: Exception) {
            emit(HomeState.Failed<String>(e.message))
        }
    }.flowOn(ioDispatcher)
}