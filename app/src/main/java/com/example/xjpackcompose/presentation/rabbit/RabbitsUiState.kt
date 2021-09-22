package com.example.xjpackcompose.presentation.rabbit

import com.example.xjpackcompose.data.remote.rabbit.Rabbit

data class RabbitsUiState(
    val rabbit: Rabbit? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)