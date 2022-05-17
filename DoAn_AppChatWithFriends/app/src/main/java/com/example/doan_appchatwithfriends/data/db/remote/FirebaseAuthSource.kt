package com.example.doan_appchatwithfriends.data.db.remote

import com.example.doan_appchatwithfriends.data.model.CreateUser
import com.example.doan_appchatwithfriends.data.model.Login
import com.example.doan_appchatwithfriends.data.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
//Firebase Auth
class FirebaseAuthStateObserver {
//Khai báo các biến để kết nối với firebase auth
    private var authListener: FirebaseAuth.AuthStateListener? = null
    private var instance: FirebaseAuth? = null

    fun start(valueEventListener: FirebaseAuth.AuthStateListener, instance: FirebaseAuth) {
        this.authListener = valueEventListener
        this.instance = instance
        this.instance!!.addAuthStateListener(authListener!!)
    }

    fun clear() {
        authListener?.let { instance?.removeAuthStateListener(it) }
    }
}

class FirebaseAuthSource {

    companion object {
        val authInstance = FirebaseAuth.getInstance()
    }

    private fun attachAuthObserver(b: ((Result<FirebaseUser>) -> Unit)): FirebaseAuth.AuthStateListener {
        return FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                b.invoke(Result.Error("No user"))
            } else { b.invoke(Result.Success(it.currentUser)) }
        }
    }

//    Login
    fun loginWithEmailAndPassword(login: Login): Task<AuthResult> {
        return authInstance.signInWithEmailAndPassword(login.email, login.password)
    }

//    Đăng ký tài khoản
    fun createUser(createUser: CreateUser): Task<AuthResult> {
        return authInstance.createUserWithEmailAndPassword(createUser.email, createUser.password)
    }
//Đăng xuất
    fun logout() {
        authInstance.signOut()
    }

    fun attachAuthStateObserver(firebaseAuthStateObserver: FirebaseAuthStateObserver, b: ((Result<FirebaseUser>) -> Unit)) {
        val listener = attachAuthObserver(b)
        firebaseAuthStateObserver.start(listener, authInstance)
    }
}

