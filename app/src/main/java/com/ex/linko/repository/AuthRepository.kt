package com.ex.linko.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    onResult(true, auth.currentUser?.uid)
                }else{
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    onResult(true, auth.currentUser?.uid)
                }else{
                    onResult(false, task.exception?.message)
                }

            }
    }

    fun logout(){
        auth.signOut()
    }

    fun currentUserId(): String? = auth.currentUser?.uid
}