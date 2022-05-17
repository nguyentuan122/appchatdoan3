@file:Suppress("unused")

package com.example.doan_appchatwithfriends.ui.chats

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doan_appchatwithfriends.R
import com.example.doan_appchatwithfriends.data.model.ChatWithUserInfo
import com.example.doan_appchatwithfriends.data.db.entity.Message
//Binding chat list
@BindingAdapter("bind_chats_list")
fun bindChatsList(listView: RecyclerView, items: List<ChatWithUserInfo>?) {
    items?.let { (listView.adapter as ChatsListAdapter).submitList(items) }
}
//Binding tin nhắn chat của bạn
@BindingAdapter("bind_chat_message_text", "bind_chat_message_text_viewModel")
fun TextView.bindMessageYouToText(message: Message, viewModel: ChatsViewModel) {
    this.text = if (message.senderID == viewModel.myUserID) {
        "You: " + message.text
    } else {
        message.text
    }
}
//Binding tin nhắn đã xem
@BindingAdapter("bind_message_view", "bind_message_textView", "bind_message", "bind_myUserID")
fun View.bindMessageSeen(view: View, textView: TextView, message: Message, myUserID: String) {
    if (message.senderID != myUserID && !message.seen) {
        view.visibility = View.VISIBLE
        textView.setTextAppearance(R.style.MessageNotSeen)
//        textView.alpha = 1f
    } else {
        view.visibility = View.INVISIBLE
        textView.setTextAppearance(R.style.MessageSeen)
//        textView.alpha = 1f
    }
}

