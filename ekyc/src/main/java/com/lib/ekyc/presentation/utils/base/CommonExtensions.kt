package com.lib.ekyc.presentation.utils.base

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.view.marginBottom


fun Any?.log(tag: String? = "app_debug") {
    Log.d(tag, this.toString())
}
fun String?.toast(ctx: Context) {
    Toast.makeText(ctx, this ?: "--", Toast.LENGTH_LONG).show()
}

fun Int.fixZero(): String {
    if (this in 1..9) {
        return "0$this"
    }
    return this.toString()
}

operator fun ViewGroup.get(position: Int): View? = getChildAt(position)


fun View.slideUp() {
    visible()
    val animate = TranslateAnimation(
        0f,
        0f,
        this.height.toFloat(),
        0f
    )
    animate.duration = 500
    animate.fillAfter = false
    this.startAnimation(animate)
}

fun View.slideDown() {
    if (visibility == View.VISIBLE) {
        invisible()
        val animate = TranslateAnimation(
            0f,
            0f,
            0f,
            this.height.toFloat() + this.marginBottom
        )
        animate.duration = 500
        animate.fillAfter = false
        this.startAnimation(animate)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}