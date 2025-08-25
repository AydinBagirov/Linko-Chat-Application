package com.ex.linko.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ex.linko.model.AuthState
import com.ex.linko.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(viewModel: AuthViewModel = viewModel()) {
    val authState by viewModel.authState.collectAsState()

    var isLoginMode by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isLoginMode) "Giriş Yap" else "Kaydol",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                if (!isLoginMode) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("İsim") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Kullanıcı Adı") },
                        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )
                }

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Şifre") },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )

                if (!isLoginMode) {
                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Şifre Tekrar") },
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )
                }

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 12.dp))
                }

                Button(
                    onClick = {
                        if (isLoginMode) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Giriş başarılı!")
                            }
                            viewModel.login(email, password)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Kayıt başarılı!")
                            }
                            viewModel.signUp(username, email, password)
                            name = ""
                            username = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(if (isLoginMode) "Giriş Yap" else "Kaydol")
                }

                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Text(text = if (isLoginMode) "Hesabınız yok mu? " else "Hesabınız var mı? ")
                    Text(
                        text = if (isLoginMode) "Kaydol" else "Giriş Yap",
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            isLoginMode = !isLoginMode
                            errorMessage = ""
                        }
                    )
                }

                Spacer(Modifier.height(16.dp))

                when (authState) {
                    AuthState.Loading -> CircularProgressIndicator()
                    is AuthState.Success -> Text("Başarıyla giriş yapıldı: ${(authState as AuthState.Success).uid}")
                    is AuthState.Error -> Text("Error: ${(authState as AuthState.Error).message}")
                    else -> {}
                }
            }
        }
    }
}
