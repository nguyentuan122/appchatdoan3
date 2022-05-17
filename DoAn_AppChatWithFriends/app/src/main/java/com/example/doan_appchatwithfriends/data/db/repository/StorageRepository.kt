package com.example.doan_appchatwithfriends.data.db.repository

import android.net.Uri
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseStorageSource
import com.example.doan_appchatwithfriends.data.Result

//Firebase storage repository
class StorageRepository {
    private val firebaseStorageService = FirebaseStorageSource()

    fun updateUserProfileImage(userID: String, byteArray: ByteArray, b: (Result<Uri>) -> Unit) {
        b.invoke(Result.Loading)
        firebaseStorageService.uploadUserImage(userID, byteArray).addOnSuccessListener {
            b.invoke((Result.Success(it)))
        }.addOnFailureListener {
            b.invoke(Result.Error(it.message))
        }
    }
}