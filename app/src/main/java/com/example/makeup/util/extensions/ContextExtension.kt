package com.example.makeup.util.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.makeup.R
import com.example.makeup.databinding.CustomSnackbarBinding
import com.example.makeup.util.Constants
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes messageResId: Int) {
    Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
}

fun Context.showCustomSnackBar(message: String, container: View?, icon: String) {
    container?.let {
        val snackView = View.inflate(this, R.layout.custom_snackbar, null)
        val binding = CustomSnackbarBinding.bind(snackView)
        val snackBar = Snackbar.make(container, "", Snackbar.LENGTH_LONG)
        snackBar.apply {
            (view as ViewGroup).addView(binding.root)
            binding.tvMessage.text = message
            if (icon == Constants.SAVE_ICON) {
                binding.imgDelete.hide()
                binding.imgSave.show()
            }
            else if (icon == Constants.DELETE_ICON) {
                binding.imgSave.hide()
                binding.imgDelete.show()
            }
            show()
        }
    }
}