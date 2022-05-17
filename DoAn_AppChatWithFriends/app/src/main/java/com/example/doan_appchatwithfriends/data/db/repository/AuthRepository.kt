package com.example.doan_appchatwithfriends.data.db.repository

import com.example.doan_appchatwithfriends.data.model.CreateUser
import com.example.doan_appchatwithfriends.data.model.Login
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseAuthSource
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseAuthStateObserver
import com.example.doan_appchatwithfriends.data.Result
import com.google.firebase.auth.FirebaseUser
//Firebase Auth Repository
class AuthRepository{
    private val firebaseAuthService = FirebaseAuthSource()

    fun observeAuthState(stateObserver: FirebaseAuthStateObserver, b: ((Result<FirebaseUser>) -> Unit)){
        firebaseAuthService.attachAuthStateObserver(stateObserver,b)
    }

    fun loginUser(login: Login, b: ((Result<FirebaseUser>) -> Unit)) {
        b.invoke(Result.Loading)
        firebaseAuthService.loginWithEmailAndPassword(login).addOnSuccessListener {
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener {
            b.invoke(Result.Error(msg = it.message))
        }
    }

    fun createUser(createUser: CreateUser, b: ((Result<FirebaseUser>) -> Unit)) {
        b.invoke(Result.Loading)
        firebaseAuthService.createUser(createUser).addOnSuccessListener {
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener {
            b.invoke(Result.Error(msg = it.message))
        }
    }

    fun logoutUser() {
        firebaseAuthService.logout()
    }
}