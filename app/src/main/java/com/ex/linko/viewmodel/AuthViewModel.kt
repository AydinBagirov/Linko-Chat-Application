package com.ex.linko.viewmodel

import androidx.lifecycle.ViewModel
import com.ex.linko.model.AuthState
import com.ex.linko.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(private val repo: AuthRepo = AuthRepo()): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun singUp(email: String, password: String){
        repo.signUp(email, password){ success, uidOrError ->
            _authState.value = if (success) AuthState.Success(uidOrError!!)
            else AuthState.Error(uidOrError ?: "Unknown error")
        }
    }

    fun login(email: String, password: String){
        repo.login(email, password){ success, uidOrError ->
            _authState.value = if (success) AuthState.Success(uidOrError!!)
            else AuthState.Error(uidOrError ?: "Unknown error")
        }
    }

    fun logout(){
        repo.logout()
        _authState.value = AuthState.Idle
    }

    fun getCurrentUser(){
        val uid = repo.currentUserId()
        _authState.value = if (uid != null) AuthState.Success(uid) else AuthState.Idle
    }
}