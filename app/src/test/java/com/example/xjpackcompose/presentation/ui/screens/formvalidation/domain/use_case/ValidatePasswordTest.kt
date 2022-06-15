package com.example.xjpackcompose.presentation.ui.screens.formvalidation.domain.use_case

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidatePasswordTest {

    private lateinit var validPassword: ValidatePassword

    @Before
    fun setUp() {
        validPassword = ValidatePassword()
    }

    @Test
    fun `Password is letter-only, returns error`() {
        val result = validPassword.execute("abcdefgh")
        assertEquals(result.successful, false)
    }
}