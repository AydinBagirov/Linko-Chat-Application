package com.ex.linko.viewmodel

import androidx.lifecycle.ViewModel
import com.ex.linko.data.User
import com.ex.linko.model.UserState
import com.ex.linko.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel(private val repo: UserRepository = UserRepository()): ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun addUser(user: User){
        _userState.value = UserState.Loading
        repo.addUser(user){ success, message ->
            _userState.value = if (success) UserState.Success(user)
            else UserState.Error(message ?: "Unknown error")
        }
    }

    fun updateUser(user: User) {
        _userState.value = UserState.Loading
        repo.updateUser(user) { success, message ->
            _userState.value = if (success) UserState.Success(user)
            else UserState.Error(message ?: "Unknown error")
        }
    }

    fun getUser(uid: String){
        _userState.value = UserState.Loading
        repo.getUser(uid) { user ->
            _userState.value = if (user != null) UserState.Success(user)
            else UserState.Error("User not found")
        }
    }

    fun saveUser(user: User){
        _userState.value = UserState.Loading

        repo.getUser(user.uid) { existingUser ->
            if (existingUser != null){
                repo.updateUser(user){ success, message ->
                    _userState.value = if(success) UserState.Success(user)
                    else UserState.Error(message ?: "Unknown Error")
                }
            }else{
                repo.addUser(user){ success, message ->
                    _userState.value = if (success) UserState.Success(user)
                    else UserState.Error(message ?: "Unknown Error")

                }
            }
        }
    }

}