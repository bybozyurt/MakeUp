package com.example.makeup.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooder.util.extensions.toast
import com.example.fooder.util.observeOnce
import com.example.makeup.R
import com.example.makeup.adapters.ProductsAdapter
import com.example.makeup.bindingadapters.ProductsBinding.Companion.handleReadDataErrors
import com.example.makeup.databinding.FragmentProductsBinding
import com.example.makeup.util.NetworkResult
import com.example.makeup.viewmodels.MainViewModel
import com.example.makeup.viewmodels.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private val mAdapter by lazy { ProductsAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        productsViewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        readDatabase()

        mainViewModel.pairMediatorLiveData.observe(viewLifecycleOwner, { (productsResponse, readProducts) ->
            handleReadDataErrors(binding.errorImageView, productsResponse, readProducts)
            handleReadDataErrors(binding.errorTextView, productsResponse, readProducts)

        })

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }


    //bottomSheet args
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readProducts.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    mAdapter.setData(database[0].products)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }


    private fun requestApiData() {
        Log.e("ahmet", "data from api")
        mainViewModel.getProducts(productsViewModel.applyQueries())
        mainViewModel.productsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    toast(requireContext(), response.message.toString())
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readProducts.observe(viewLifecycleOwner, {database->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].products)
                }
            })
        }
    }

    private fun showShimmerEffect() {
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}