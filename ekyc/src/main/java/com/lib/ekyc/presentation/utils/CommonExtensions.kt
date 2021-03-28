package com.lib.ekyc.presentation.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar

fun Context.inflate(res: Int, parent: ViewGroup? = null): View {
    return LayoutInflater.from(this).inflate(res, parent, false)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

inline fun Dialog.ifIsShowing(body: Dialog.() -> Unit) {
    if (isShowing) {
        body()
    }
}

inline fun Snackbar.ifIsShowing(body: Snackbar.() -> Unit) {
    if (isShown) {
        body()
    }
}

fun Any?.log(tag: String? = "app_debug") {
    Log.d(tag, this.toString())
}

fun String?.toast(ctx: Context) {
    Toast.makeText(ctx, this?:"--", Toast.LENGTH_LONG).show()
}

fun Int.fixTwoZero(): String {
    if (this == 0) return "00"
    if (this < 10) return "0${this}"
    return this.toString()
}

fun ListView.setHeightBasedOnChild() {
    val mAdapter = this.adapter
    var totalHeight = 0
    for (i in 0 until mAdapter.count) {
        val mView = mAdapter.getView(i, null, this)
        mView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        totalHeight += mView.measuredHeight
    }
    val params = this.layoutParams
    params.height = totalHeight - 110
    this.layoutParams = params
    this.requestLayout()
}

operator fun ViewGroup.get(position: Int): View? = getChildAt(position)