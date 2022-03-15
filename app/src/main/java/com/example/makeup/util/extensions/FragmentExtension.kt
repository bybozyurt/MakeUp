package com.example.makeup.util.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(context: Context, @StringRes messageResId: Int) {
    Toast.makeText(context, getString(messageResId), Toast.LENGTH_SHORT).show()
}