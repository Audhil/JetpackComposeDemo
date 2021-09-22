package com.example.xjpackcompose.domain.use_case

import com.example.xjpackcompose.data.remote.rabbit.Rabbit
import com.example.xjpackcompose.domain.repository.RabbitRepository
import com.example.xjpackcompose.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRabbitUseCase
@Inject
constructor(
    private val rabbitRepository: RabbitRepository
) {
    operator fun invoke(url: String) = flow {
        emit(Resource.Loading<Rabbit>())
        try {
            emit(Resource.Success<Rabbit>(data = rabbitRepository.getRabbit(url)))
        } catch (e: Exception) {
            emit(Resource.Error<Rabbit>(message = "Something happened, BAD!"))
        }
    }
}