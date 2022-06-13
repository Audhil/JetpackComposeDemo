package com.example.xjpackcompose.presentation.ui.screens.formvalidation.domain.use_case

import javax.inject.Inject

class ValidateTerms
@Inject
constructor(){

    fun execute(acceptedTerms: Boolean): ValidationResult {
        if (!acceptedTerms)
            return ValidationResult(
                false,
                "Pls, accept the terms"
            )
        return ValidationResult(true)
    }
}