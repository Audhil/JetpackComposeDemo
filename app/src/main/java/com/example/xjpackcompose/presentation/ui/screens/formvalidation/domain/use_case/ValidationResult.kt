package com.example.xjpackcompose.presentation.ui.screens.formvalidation.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)