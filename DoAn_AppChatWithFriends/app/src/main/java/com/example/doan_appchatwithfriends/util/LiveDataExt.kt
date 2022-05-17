package com.example.doan_appchatwithfriends.util

import androidx.lifecycle.MutableLiveData

//Thêm item mới
fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
    val newList = mutableListOf<T>()
    this.value?.let { newList.addAll(it) }
    newList.add(item)
    this.value = newList
}
//Cập nhật item
fun <T> MutableLiveData<MutableList<T>>.updateItemAt(item: T, index: Int) {
    val newList = mutableListOf<T>()
    this.value?.let { newList.addAll(it) }
    newList[index] = item
    this.value = newList
}
//Xóa item
fun <T> MutableLiveData<MutableList<T>>.removeItem(item: T) {
    val newList = mutableListOf<T>()
    this.value?.let { newList.addAll(it) }
    newList.remove(item)
    this.value = newList
}
