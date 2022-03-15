package com.example.makeup.ui.products

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makeup.util.extensions.toast
import com.example.makeup.util.extensions.observeOnce
import com.example.makeup.R
import com.example.makeup.ui.adapters.ProductsAdapter
import com.example.makeup.databinding.FragmentProductsBinding
import com.example.makeup.util.NetworkListener
import com.example.makeup.util.NetworkResult
import com.example.makeup.util.extensions.handleReadDataErrors
import com.example.makeup.ui.viewmodels.MainViewModel
import com.example.makeup.ui.viewmodels.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val args by navArgs<ProductsFragmentArgs>()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private val mAdapter by lazy { ProductsAdapter() }
    private lateinit var networkListener: NetworkListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        productsViewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        readDatabase()
        readBackOnline()
        checkNetworkStatus()
        navigateBottomSheet()

        mainViewModel.pairMediatorLiveData.observe(viewLifecycleOwner) { (productsResponse, readProducts) ->
            with(binding) {
                errorImageView.handleReadDataErrors(productsResponse, readProducts)
                errorTextView.handleReadDataErrors(productsResponse, readProducts)
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.adapter = mAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
        }
        showShimmerEffect()
    }


    //bottomSheet args
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readProducts.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("ProductsFragment", "readDatabase called!")
                    mAdapter.setData(database[0].products)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }


    private fun requestApiData() {
        with(mainViewModel) {
            getProducts(productsViewModel.applyQueries())
            productsResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        hideShimmerEffect()
                        response.data?.let { mAdapter.setData(it) }
                        productsViewModel.saveBrandAndCategory()
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

            }
        }

    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readProducts.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].products)
                }
            }
        }
    }

    private fun navigateBottomSheet() {
        binding.floatingActionButton.setOnClickListener {
            if (productsViewModel.networkStatus) {
                findNavController().navigate(R.id.action_productsFragment_to_productBottomSheet)
            } else {
                productsViewModel.showNetworkStatus()
            }

        }

    }

    private fun checkNetworkStatus() {
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{ status ->
                    Log.e("NetworkListener",status.toString())
                    with(productsViewModel) {
                        networkStatus = status
                        showNetworkStatus()
                    }
                    readDatabase()
                }
        }
    }

    private fun readBackOnline() {
        with(productsViewModel) {
            readBackOnline.observe(viewLifecycleOwner) {
                backOnline = it
            }
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