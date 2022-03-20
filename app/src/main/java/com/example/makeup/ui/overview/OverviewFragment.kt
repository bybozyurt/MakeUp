package com.example.makeup.ui.overview


import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.makeup.R
import com.example.makeup.databinding.FragmentOverviewBinding
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.ui.base.BaseBindingFragment
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY
import com.example.makeup.util.extensions.loadImageFromUrl
import com.example.makeup.util.extensions.parseHtml

class OverviewFragment : BaseBindingFragment<FragmentOverviewBinding>() {

    private lateinit var productsItem: ProductsItem

    private fun initBundle() {
        val args = arguments
        productsItem = args!!.getParcelable<ProductsItem>(PRODUCTS_BUNDLE_KEY) as ProductsItem
    }

    private fun setupBinding() {
        mBinding?.let {
            with(it) {
                productsItem.imageLink?.let { mainImageView.loadImageFromUrl(it) }
                titleTextView.text = productsItem.name
                descriptionTextView.parseHtml(productsItem.description)
            }
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

    private fun initTagColors() {
        with(productsItem.tagList!!) {
            mBinding?.let {
                with(it) {
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
                            natImageView
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

        }
    }

    override fun getContentLayoutResId() = R.layout.fragment_overview

    override fun populateUI() {
        initBundle()
        setupBinding()
        initTagColors()
    }

    override fun onDestView() {
        mBinding = null
    }


}