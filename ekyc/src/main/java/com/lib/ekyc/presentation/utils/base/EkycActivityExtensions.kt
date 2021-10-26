package com.lib.ekyc.presentation.utils.base

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.view.marginBottom



@Suppress("DEPRECATION")
fun Context?.width(): Int {
    val windowManager = this?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

@Suppress("DEPRECATION")
fun Context?.height(): Int {
    val windowManager = this?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun String?.bodyOrNull(): String {
    return if (this.isNullOrEmpty())
        ""
    else
        this
}

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