package com.ex.linko.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ex.linko.model.AuthState
import com.ex.linko.viewmodel.AuthViewModel

@Composable
fun AuthScreen(viewModel: AuthViewModel = viewModel()){
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        TextField(value = email, onValueChange = {email = it}, label = {Text("Email")})
        TextField(value = password, onValueChange = {password = it}, label = {Text("Password")},
            visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = {viewModel.singUp(email, password)}) {
                Text("Sign Up")
            }
            Button(onClick = {viewModel.login(email, password)}) {
                Text("Login") }
        }
        Spacer(Modifier.height(16.dp))

        when (authState) {
            AuthState.Idle -> Text("Not Logged In")
            AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> Text("Logged in UID ${(authState as AuthState.Success).uid}")
            is AuthState.Error -> Text("Error: ${(authState as AuthState.Error).message}")
        }
    }
}