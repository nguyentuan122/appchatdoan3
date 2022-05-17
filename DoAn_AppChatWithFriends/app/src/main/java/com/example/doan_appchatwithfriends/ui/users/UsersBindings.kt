package com.example.doan_appchatwithfriends.ui.users

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doan_appchatwithfriends.data.db.entity.User
//Người dùng binding
//Binding danh sách người dùng
@BindingAdapter("bind_users_list")
fun bindUsersList(listView: RecyclerView, items: List<User>?) {
    items?.let { (listView.adapter as UsersListAdapter).submitList(items) }
}

