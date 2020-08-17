package com.developer.ivan.beerapp.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.developer.ivan.beerapp.R

val Fragment.application: App
    get() = (requireActivity().application as App)


fun <T> TextView.setNotNullText(obj: T?, callback: TextView.(T) -> String?) {
    when (obj) {
        null -> this.visibility = View.GONE
        else -> text = callback(obj)
    }
}

fun Float.formatDecimals(numOfDec: Int): String {
    if (numOfDec < 0) {
        return this.toString()
    }
    return "%.${numOfDec}f".format(this)
}

fun Double.formatDecimals(numOfDec: Int): String {
    if (numOfDec < 0) {
        return this.toString()
    }
    return "%.${numOfDec}f".format(this)
}

fun ImageView.loadImageOrHide(src: String?) {

    if (src == null)
        this.isVisible = false
    else {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(src)
            .error(R.drawable.ic_baseline_error_24)
            .placeholder(circularProgressDrawable)
            .into(this)
    }

}