package com.example.doan_appchatwithfriends.util

import com.google.firebase.database.DataSnapshot
//Chuyển đổi snapshot thành class
fun <T> wrapSnapshotToClass(className: Class<T>, snap: DataSnapshot): T? {
    return snap.getValue(className)
}
//Chuyển đổi snapshot thành array list
fun <T> wrapSnapshotToArrayList(className: Class<T>, snap: DataSnapshot): MutableList<T> {
    val arrayList: MutableList<T> = arrayListOf()
    for (child in snap.children) {
        child.getValue(className)?.let { arrayList.add(it) }
    }
    return arrayList
}

// Always returns the same combined id when comparing the two users id's
fun convertTwoUserIDs(userID1: String, userID2: String): String {
    return if (userID1 < userID2) {
        userID2 + userID1
    } else {
        userID1 + userID2
    }
}