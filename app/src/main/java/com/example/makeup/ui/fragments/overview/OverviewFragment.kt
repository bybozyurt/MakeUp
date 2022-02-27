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
import com.example.makeup.databinding.FragmentOverviewBinding
import com.example.makeup.models.ProductsItem
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY
import com.example.makeup.util.extensions.loadImageFromUrl
import com.example.makeup.util.extensions.parseHtml

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

        with(productsItem.tagList!!) {
            with(binding) {
                with(resources) {
                    updateColors(
                        contains(getString(R.string.organic)),
                        organicTextView,
                        organicImageView
                    )
                    updateColors(
                        contains(getString(R.string.vegan)),
                        veganTextView,
                        veganImageView
                    )
                    updateColors(
                        contains(getString(R.string.canadian)),
                        canadianFreeTextView,
                        canadianFreeImageView
                    )
                    updateColors(
                        contains(getString(R.string.gluten_free)),
                        glutenFreeTextView,
                        glutenFreeImageView
                    )
                    updateColors(
                        contains(getString(R.string.natural)),
                        natTextView,
                        binding.natImageView
                    )
                    updateColors(
                        contains(getString(R.string.cruelty_free_query)),
                        crueltyFreeTextView,
                        crueltyFreeImageView
                    )
                    updateColors(
                        contains(getString(R.string.hypoallergenic)),
                        hypoAllergenicTextView,
                        hypoAllergenicImageView
                    )
                    updateColors(
                        contains(getString(R.string.ewg_verified)),
                        ewgTextView,
                        ewgImageView
                    )
                }
            }

        }

        return binding.root
    }

    private fun initBundle() {
        val args = arguments
        productsItem = args!!.getParcelable<ProductsItem>(PRODUCTS_BUNDLE_KEY) as ProductsItem
    }

    private fun setupBinding() {
        with(binding) {
            mainImageView.loadImageFromUrl(productsItem.imageLink)
            titleTextView.text = productsItem.name
            binding.descriptionTextView.parseHtml(productsItem.description)
        }

    }


    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkGray))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}