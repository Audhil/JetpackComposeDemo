package com.example.xjpackcompose.presentation.ui.screens.formvalidation.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FormValidationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UI()
        }
    }

    @Composable
    fun UI(formValidationVM: FormValidationVM = hiltViewModel()) {
        val state = formValidationVM.state
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            formValidationVM.validateEvents.collect { event ->
                when (event) {
                    is FormValidationVM.ValidationEvent.Success -> {
                        Toast
                            .makeText(context, "Registration Successful!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            //  email
            TextField(
                value = state.email,
                onValueChange = {
                    formValidationVM.onEvent(RegistrationFormEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            if (state.emailError != null)
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            Spacer(modifier = Modifier.height(16.dp))

            //  pwd
            TextField(
                value = state.password,
                onValueChange = {
                    formValidationVM.onEvent(RegistrationFormEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()   //  show dots in password field
            )
            if (state.passwordError != null)
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            Spacer(modifier = Modifier.height(16.dp))

            //  repeat pwd
            TextField(
                value = state.repeatedPassword,
                onValueChange = {
                    formValidationVM.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                },
                isError = state.repeatedPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "RepeatPassword") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()   //  show dots in password field
            )
            if (state.repeatedPasswordError != null)
                Text(
                    text = state.repeatedPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            Spacer(modifier = Modifier.height(16.dp))

            //  check box
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = state.acceptedTerms, onCheckedChange = {
                    formValidationVM.onEvent(RegistrationFormEvent.AcceptTerms(it))
                })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Accept terms")
            }
            if (state.termsError != null)
                Text(text = state.termsError, color = MaterialTheme.colors.error)

            //  button
            Button(
                onClick = { formValidationVM.onEvent(RegistrationFormEvent.Submit) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Submit")
            }
        }
    }
}
