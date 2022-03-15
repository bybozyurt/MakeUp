package com.example.makeup.ui.website

import android.webkit.WebViewClient
import com.example.makeup.R
import com.example.makeup.databinding.FragmentWebsiteBinding
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.ui.base.BaseBindingFragment
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY

class WebsiteFragment : BaseBindingFragment<FragmentWebsiteBinding>() {

    private var productsItem: ProductsItem? = null

    private fun initWebView() {
        mBinding?.instructionsWebView?.let {
            with(it) {
                webViewClient = object : WebViewClient() {}
                val websiteUrl: String = productsItem!!.productLink.toString()
                loadUrl(websiteUrl)
            }
        }
    }

    private fun initBundle() {
        val args = arguments
        productsItem = args?.getParcelable(PRODUCTS_BUNDLE_KEY)
    }

    override fun getContentLayoutResId() = R.layout.fragment_website

    override fun populateUI() {
        initBundle()
        initWebView()
    }

    override fun onDestView() {
        mBinding = null
    }


}