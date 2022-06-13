package com.example.xjpackcompose.presentation.ui.screens.formvalidation.domain.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail
@Inject
constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isBlank())
            return ValidationResult(
                false,
                "The email can't be blank"
            )
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidationResult(
                false,
                "That's not a valid email"
            )
        return ValidationResult(true)
    }
}