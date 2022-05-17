package com.example.doan_appchatwithfriends.data

//Trả về các trạng thái kết quả
sealed class Result<out R> {
//    Thành công
    data class Success<out T>(val data: T? = null, val msg: String? = null) : Result<T>()
//    Lỗi
    class Error(val msg: String? = null) : Result<Nothing>()
//    Loading
    object Loading : Result<Nothing>()
}
