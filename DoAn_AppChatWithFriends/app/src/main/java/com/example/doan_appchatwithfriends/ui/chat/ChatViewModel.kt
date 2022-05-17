package com.example.doan_appchatwithfriends.ui.chat

import androidx.lifecycle.*
import com.example.doan_appchatwithfriends.data.db.entity.Chat
import com.example.doan_appchatwithfriends.data.db.entity.Message
import com.example.doan_appchatwithfriends.data.db.entity.UserInfo
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseReferenceChildObserver
import com.example.doan_appchatwithfriends.data.db.remote.FirebaseReferenceValueObserver
import com.example.doan_appchatwithfriends.data.db.repository.DatabaseRepository
import com.example.doan_appchatwithfriends.ui.DefaultViewModel
import com.example.doan_appchatwithfriends.data.Result
import com.example.doan_appchatwithfriends.util.addNewItem

//Chat viewmodel
class ChatViewModelFactory(private val myUserID: String, private val otherUserID: String, private val chatID: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(myUserID, otherUserID, chatID) as T
    }
}

class ChatViewModel(private val myUserID: String, private val otherUserID: String, private val chatID: String) : DefaultViewModel() {

    private val dbRepository: DatabaseRepository = DatabaseRepository()

    private val _otherUser: MutableLiveData<UserInfo> = MutableLiveData()
    private val _addedMessage = MutableLiveData<Message>()

    private val fbRefMessagesChildObserver = FirebaseReferenceChildObserver()
    private val fbRefUserInfoObserver = FirebaseReferenceValueObserver()

    val messagesList = MediatorLiveData<MutableList<Message>>()
    val newMessageText = MutableLiveData<String>()
    val otherUser: LiveData<UserInfo> = _otherUser
//Khởi tạo
    init {
//    Gọi hàm setup chat và check update
        setupChat()
        checkAndUpdateLastMessageSeen()
    }

    override fun onCleared() {
        super.onCleared()
        fbRefMessagesChildObserver.clear()
        fbRefUserInfoObserver.clear()
    }

    private fun checkAndUpdateLastMessageSeen() {
        dbRepository.loadChat(chatID) { result: Result<Chat> ->
            if (result is Result.Success && result.data != null) {
                result.data.lastMessage.let {
                    if (!it.seen && it.senderID != myUserID) {
                        it.seen = true
                        dbRepository.updateChatLastMessage(chatID, it)
                    }
                }
            }
        }
    }

//    Setup chat có tác dụng để load ra tin nhắn mới
    private fun setupChat() {
        dbRepository.loadAndObserveUserInfo(otherUserID, fbRefUserInfoObserver) { result: Result<UserInfo> ->
            onResult(_otherUser, result)
            if (result is Result.Success && !fbRefMessagesChildObserver.isObserving()) {
                loadAndObserveNewMessages()
            }
        }
    }

    private fun loadAndObserveNewMessages() {
        messagesList.addSource(_addedMessage) { messagesList.addNewItem(it) }

        dbRepository.loadAndObserveMessagesAdded(
            chatID,
            fbRefMessagesChildObserver
        ) { result: Result<Message> ->
            onResult(_addedMessage, result)
        }
    }
//Hàm này sẽ được gọi khi nhấn nút gửi tin nhắn đi
    fun sendMessagePressed() {
        if (!newMessageText.value.isNullOrBlank()) {
            val newMsg = Message(myUserID, newMessageText.value!!)
            dbRepository.updateNewMessage(chatID, newMsg)
            dbRepository.updateChatLastMessage(chatID, newMsg)
            newMessageText.value = ""
        }
    }
}