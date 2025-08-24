package com.ex.linko.model

sealed class AuthState {
    object Idle : AuthState()                           // Henüz giriş yapılmamış
    object Loading: AuthState()                         // Kayıt veya giriş işlemi devam ediyor
    data class Success(val uid: String) : AuthState()   // Başarılı giriş/kayıt
    data class Error(val message: String) : AuthState() //Hata durumu
}