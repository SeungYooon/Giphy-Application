package com.example.giphyapplication.utils.extension

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun Context.toPixels(unit: Int = TypedValue.COMPLEX_UNIT_DIP, value: Number): Int =
    TypedValue.applyDimension(unit, value.toFloat(), resources.displayMetrics).toInt()

fun View.showSnackBar(@StringRes resId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    showSnackBar(this.resources.getString(resId), duration)
}

fun View.showSnackBar(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, duration).show()
}