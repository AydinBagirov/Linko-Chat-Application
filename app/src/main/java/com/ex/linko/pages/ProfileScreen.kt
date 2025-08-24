package com.ex.linko.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ex.linko.data.User
import com.ex.linko.model.UserState
import com.ex.linko.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileScreen(viewModel: UserViewModel = viewModel(), currentUser: FirebaseUser){
    val userState by viewModel.userState.collectAsState()

    var name by remember { mutableStateOf("") }

    LaunchedEffect(currentUser.uid) {
        viewModel.getUser(currentUser.uid)
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        when(userState){
            is UserState.Loading -> CircularProgressIndicator()
            is UserState.Success -> {
                val user = (userState as UserState.Success).user
                name = user.name
                Text("Email: ${user.email}")
            }
            is UserState.Error -> Text("Error: ${(userState as UserState.Error).message}.")
            else -> {}
        }
        Spacer(Modifier.height(16.dp))
        TextField(value = name, onValueChange = {name = it}, label = {Text("Name")})
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            viewModel.saveUser(User(currentUser.uid, name, currentUser.email ?: ""))
        }) {
            Text("Save Profile")
        }
    }
}