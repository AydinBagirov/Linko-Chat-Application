package com.ex.linko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ex.linko.model.AuthState
import com.ex.linko.pages.AuthScreen
import com.ex.linko.pages.ProfileScreen
import com.ex.linko.ui.theme.LinkoTheme
import com.ex.linko.viewmodel.AuthViewModel
import com.ex.linko.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ViewModel'leri alıyoruz
            val authViewModel: AuthViewModel = viewModel()
            val userViewModel: UserViewModel = viewModel()

            // AuthState'i dinliyoruz
            val authState by authViewModel.authState.collectAsState()

            // Auth durumuna göre ekranı seçiyoruz
            when(authState) {
                is AuthState.Success -> ProfileScreen(
                    viewModel = userViewModel,
                    currentUser = FirebaseAuth.getInstance().currentUser!!
                )
                else -> AuthScreen(viewModel = authViewModel)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkoTheme {

    }
}