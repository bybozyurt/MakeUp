package com.example.makeup.ui.colors

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makeup.R
import com.example.makeup.ui.adapters.ColorsAdapter
import com.example.makeup.databinding.FragmentColorsBinding
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.ui.base.BaseBindingFragment
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY
import com.example.makeup.util.extensions.show


class ColorsFragment : BaseBindingFragment<FragmentColorsBinding>() {

    private val mAdapter: ColorsAdapter by lazy { ColorsAdapter() }
    private var productsItem: ProductsItem? = null

    override fun getContentLayoutResId() = R.layout.fragment_colors

    override fun populateUI() {
        setupRecyclerView()
        initBundle()
    }

    private fun setupRecyclerView() {
        mBinding?.let {
            with(it.colorsRecyclerView) {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    }

    private fun initBundle() {
        val args = arguments
        productsItem = args?.getParcelable(PRODUCTS_BUNDLE_KEY)
        productsItem?.productColors?.let {
            mAdapter.setData(it)
            if (it.isNullOrEmpty()) {
                mBinding?.apply {
                    errorImageView.show()
                    errorTextView.show()
                }
            }
        }
    }

    override fun onDestView() {
        mBinding = null
    }


}