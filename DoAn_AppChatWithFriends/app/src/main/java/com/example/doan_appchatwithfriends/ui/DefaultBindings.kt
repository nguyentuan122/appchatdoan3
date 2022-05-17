package com.example.doan_appchatwithfriends.ui

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doan_appchatwithfriends.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

//Binding làm mờ ảnh
@BindingAdapter("bind_image_url_blur")
fun bindBlurImageWithPicasso(imageView: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        Picasso.get().load(url).error(R.drawable.ic_baseline_error_24)
            .transform(BlurTransformation(imageView.context, 15, 1)).into(imageView)
    }
}
//Binding hiển thị avt từ url
@BindingAdapter("bind_image_url")
fun bindImageWithPicasso(imageView: ImageView, url: String?) {
    when (url) {
        null -> Unit
        "" -> imageView.setBackgroundResource(R.drawable.ic_baseline_person_24)
        else -> Picasso.get().load(url).error(R.drawable.ic_baseline_error_24).into(imageView)
    }
}
//Binding định dạng ngày hôm qua
@SuppressLint("SimpleDateFormat")
@BindingAdapter("bind_epochTimeMsToDate_with_days_ago")
fun TextView.bindEpochTimeMsToDateWithDaysAgo(epochTimeMs: Long) {
    val numOfDays = TimeUnit.MILLISECONDS.toDays(Date().time - epochTimeMs)

    this.text = when {
        numOfDays == 1.toLong() -> "Yesterday"
        numOfDays > 1.toLong() -> "$numOfDays days ago"
        else -> {
            val pat =
                SimpleDateFormat().toLocalizedPattern().replace("\\W?[YyMd]+\\W?".toRegex(), " ")
            val formatter = SimpleDateFormat(pat, Locale.getDefault())
            formatter.format(Date(epochTimeMs))
        }
    }
}
//Binding định dạng ngày hôm nay (theo giờ, theo phút)
@SuppressLint("SimpleDateFormat")
@BindingAdapter("bind_epochTimeMsToDate")
fun TextView.bindEpochTimeMsToDate(epochTimeMs: Long) {
    if (epochTimeMs > 0) {
        val currentTimeMs = Date().time
        val numOfDays = TimeUnit.MILLISECONDS.toDays(currentTimeMs - epochTimeMs)

        val replacePattern = when {
            numOfDays >= 1.toLong() -> "Yy"
            else -> "YyMd"
        }
        val pat = SimpleDateFormat().toLocalizedPattern().replace("\\W?[$replacePattern]+\\W?".toRegex(), " ")
        val formatter = SimpleDateFormat(pat, Locale.getDefault())
        this.text = formatter.format(Date(epochTimeMs))
    }
}
//Binding các hiệu ứng chuyển động
@BindingAdapter("bind_disable_item_animator")
fun bindDisableRecyclerViewItemAnimator(recyclerView: RecyclerView, disable: Boolean) {
    if (disable) {
        recyclerView.itemAnimator = null
    }
}
