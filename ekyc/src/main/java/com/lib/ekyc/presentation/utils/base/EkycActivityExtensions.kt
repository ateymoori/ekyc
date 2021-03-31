package com.lib.ekyc.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.marginBottom
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

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

fun Activity.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.longToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
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