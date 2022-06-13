package com.example.xjpackcompose.presentation.ui.screens.formvalidation.domain.use_case

import javax.inject.Inject

class ValidateRepeatedPassword
@Inject
constructor(){

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword)
            return ValidationResult(
                false,
                "The passwords don't match"
            )
        return ValidationResult(true)
    }
}