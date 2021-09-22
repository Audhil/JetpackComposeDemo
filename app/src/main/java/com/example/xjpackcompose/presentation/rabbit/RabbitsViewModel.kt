package com.example.xjpackcompose.presentation.rabbit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xjpackcompose.domain.use_case.GetRabbitUseCase
import com.example.xjpackcompose.util.Constants
import com.example.xjpackcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RabbitsViewModel
@Inject
constructor(
    private val getRabbitUseCase: GetRabbitUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RabbitsUiState())
    val state: State<RabbitsUiState> = _state

    init {
        getRandomRabbit()
    }

    fun getRandomRabbit(url: String = Constants.RABBIT_BASE_URL) {
        viewModelScope.launch {
            getRabbitUseCase(url).collect { result ->
                when (result) {
                    is Resource.Loading ->
                        _state.value = RabbitsUiState(isLoading = true)

                    is Resource.Success ->
                        _state.value = RabbitsUiState(isLoading = false, rabbit = result.data)

                    is Resource.Error ->
//                        _state.value = state.value.copy(isLoading = false)
                        _state.value = RabbitsUiState(isLoading = false)
                }
            }
        }
    }
}