package com.ex.linko.repository

import com.ex.linko.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    fun addUser(user: User, onResult: (Boolean, String?) -> Unit){
        usersRef.document(user.uid).set(user)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { exception -> onResult(false, exception.message) }
    }

    fun updateUser(user: User, onResult: (Boolean, String?) -> Unit){
        usersRef.document(user.uid).update(
            mapOf(
                "name" to user.name,
                "profileImageUrl" to user.profileImageUrl
            )
        ).addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { exception -> onResult(false, exception.message) }
    }

    fun getUser(uid: String, onResult: (User?) -> Unit){
        usersRef.document(uid).get()
            .addOnSuccessListener { snapshot ->
                onResult(snapshot.toObject(User::class.java))
            }.addOnFailureListener { onResult(null) }
    }
}