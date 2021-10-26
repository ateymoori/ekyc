package com.lib.ekyc.presentation.utils.base

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


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
