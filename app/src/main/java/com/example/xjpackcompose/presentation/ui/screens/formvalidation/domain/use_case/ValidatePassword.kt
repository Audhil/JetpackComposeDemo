package com.example.xjpackcompose.presentation.ui.screens.formvalidation.domain.use_case

import javax.inject.Inject

class ValidatePassword
@Inject
constructor(){

    fun execute(password: String): ValidationResult {
        if (password.length < 8)
            return ValidationResult(
                false,
                "The password needs to consist of at least 8 characters"
            )
        val containsLettersAndDigits = password.any {
            it.isDigit()
        } && password.any {
            it.isLetter()
        }

        if (!containsLettersAndDigits)
            return ValidationResult(
                false,
                "Passwod must contain at least a digit and a letter"
            )
        return ValidationResult(true)
    }
}