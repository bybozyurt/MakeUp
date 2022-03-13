package com.example.makeup.util.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.makeup.R
import com.example.makeup.databinding.CustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar


fun Context.showCustomSnackBar(message: String, container: View?) {
    container?.let {
        val snackView = View.inflate(this, R.layout.custom_snackbar, null)
        val binding = CustomSnackbarBinding.bind(snackView)
        val snackBar = Snackbar.make(container, "", Snackbar.LENGTH_LONG)
        snackBar.apply {
            (view as ViewGroup).addView(binding.root)
            binding.tvMessage.text = message
            show()
        }
    }
}