package com.example.makeup.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.makeup.databinding.FragmentWebsiteBinding

import com.example.makeup.models.ProductsItem
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY

class WebsiteFragment : Fragment() {

    private var _binding: FragmentWebsiteBinding? = null
    private val binding get() = _binding!!
    private var myBundle: ProductsItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebsiteBinding.inflate(inflater, container, false)
        initBundle()
        initWebView()


        return binding.root
    }

    private fun initWebView(){
        with(binding.instructionsWebView){
            webViewClient = object : WebViewClient() { }
            val websiteUrl: String = myBundle!!.productLink.toString()
            loadUrl(websiteUrl)
        }
    }

    private fun initBundle(){
        val args = arguments
        myBundle = args?.getParcelable(PRODUCTS_BUNDLE_KEY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}