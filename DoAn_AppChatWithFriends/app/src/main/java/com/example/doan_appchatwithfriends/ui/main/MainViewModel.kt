package com.example.doan_appchatwithfriends.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doan_appchatwithfriends.App
import com.example.doan_appchatwithfriends.data.db.entity.UserNotification
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseAuthStateObserver
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseReferenceConnectedObserver
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseReferenceValueObserver
import com.example.doan_appchatwithfriends.data.db.repository.AuthRepository
import com.example.doan_appchatwithfriends.data.db.repository.DatabaseRepository
import com.example.doan_appchatwithfriends.data.Result
import com.google.firebase.auth.FirebaseUser

//Main view model
class MainViewModel : ViewModel() {

    private val dbRepository = DatabaseRepository()
    private val authRepository = AuthRepository()

    private val _userNotificationsList = MutableLiveData<MutableList<UserNotification>>()

    private val fbRefNotificationsObserver = FirebaseReferenceValueObserver()
    private val fbAuthStateObserver = FirebaseAuthStateObserver()
    private val fbRefConnectedObserver = FirebaseReferenceConnectedObserver()
    private var userID = App.myUserID

    var userNotificationsList: LiveData<MutableList<UserNotification>> = _userNotificationsList

    init {
       setupAuthObserver()
    }

    override fun onCleared() {
        super.onCleared()
        fbRefNotificationsObserver.clear()
        fbRefConnectedObserver.clear()
        fbAuthStateObserver.clear()
    }

    private fun setupAuthObserver(){
        authRepository.observeAuthState(fbAuthStateObserver) { result: Result<FirebaseUser> ->
            if (result is Result.Success) {
                userID = result.data!!.uid
                startObservingNotifications()
                fbRefConnectedObserver.start(userID)
            } else {
                fbRefConnectedObserver.clear()
                stopObservingNotifications()
            }
        }
    }

    private fun startObservingNotifications() {
        dbRepository.loadAndObserveUserNotifications(userID, fbRefNotificationsObserver) { result: Result<MutableList<UserNotification>> ->
            if (result is Result.Success) {
                _userNotificationsList.value = result.data
            }
        }
    }

    private fun stopObservingNotifications() {
        fbRefNotificationsObserver.clear()
    }
}
