package com.example.makeup.util.extensions

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(message: String, setActionMode: String, view: View){

    Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_SHORT
    ).setAction(setActionMode){}
        .show()

}

fun Context.showSnackBar(message: String, setActionMode: String, view: View){

    Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_SHORT
    ).setAction(setActionMode){}
        .show()
}