package com.example.doan_appchatwithfriends.data.model

import com.example.doan_appchatwithfriends.data.db.entity.Chat
import com.example.doan_appchatwithfriends.data.db.entity.UserInfo

data class ChatWithUserInfo(
    var mChat: Chat,
    var mUserInfo: UserInfo
)
