package com.example.makeup.ui.fragments.colors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makeup.R
import com.example.makeup.adapters.ColorsAdapter
import com.example.makeup.databinding.FragmentColorsBinding
import com.example.makeup.models.ProductColor
import com.example.makeup.models.ProductsItem
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY


class ColorsFragment : Fragment() {
    private val mAdapter: ColorsAdapter by lazy { ColorsAdapter() }

    private var _binding: FragmentColorsBinding? = null
    private val binding get() = _binding!!
    private var myBundle: ProductsItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentColorsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        initBundle()



        return binding.root
    }

    private fun setupRecyclerView() {
        with(binding.colorsRecyclerView){
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun initBundle(){
        val args = arguments
        myBundle = args?.getParcelable(PRODUCTS_BUNDLE_KEY)
        myBundle?.productColors?.let {
            mAdapter.setData(it)
            if (it.isNullOrEmpty()){
                binding.errorImageView.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}