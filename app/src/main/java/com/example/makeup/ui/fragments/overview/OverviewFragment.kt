package com.example.makeup.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.makeup.R
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.loadImageFromUrl
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.parseHtml
import com.example.makeup.databinding.FragmentOverviewBinding
import com.example.makeup.models.ProductsItem
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var productsItem: ProductsItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        initBundle()
        setupBinding()

        with(productsItem.tagList!!){
            with(binding){
                updateColors(contains("Organic"), organicTextView, organicImageView)
                updateColors(contains("Vegan"), veganTextView, veganImageView)
                updateColors(contains("Canadian"), canadianFreeTextView, canadianFreeImageView)
                updateColors(contains("Gluten Free"), glutenFreeTextView, glutenFreeImageView)
                updateColors(contains("Natural"), natTextView, binding.natImageView)
                updateColors(contains("cruelty free"), crueltyFreeTextView, crueltyFreeImageView)
                updateColors(contains("Hypoallergenic"), hypoAllergenicTextView, hypoAllergenicImageView)
                updateColors(contains("EWG Verified"), ewgTextView, ewgImageView)

            }

        }


        return binding.root
    }

    private fun initBundle() {
        val args = arguments
        productsItem = args!!.getParcelable<ProductsItem>(PRODUCTS_BUNDLE_KEY) as ProductsItem
    }

    private fun setupBinding(){
        with(binding) {
            loadImageFromUrl(mainImageView, productsItem.imageLink)
            titleTextView.text = productsItem.name
            parseHtml(binding.descriptionTextView, productsItem.description)
        }

    }


    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}