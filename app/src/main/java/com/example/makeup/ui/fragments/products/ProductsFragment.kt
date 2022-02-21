package com.example.makeup.ui.fragments.products

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makeup.util.extensions.toast
import com.example.makeup.util.extensions.observeOnce
import com.example.makeup.R
import com.example.makeup.adapters.ProductsAdapter
import com.example.makeup.bindingadapters.ProductsBinding.Companion.handleReadDataErrors
import com.example.makeup.databinding.FragmentProductsBinding
import com.example.makeup.util.NetworkListener
import com.example.makeup.util.NetworkResult
import com.example.makeup.viewmodels.MainViewModel
import com.example.makeup.viewmodels.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductsFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<ProductsFragmentArgs>()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private val mAdapter by lazy { ProductsAdapter() }
    private lateinit var networkListener: NetworkListener


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
        setHasOptionsMenu(true)
        setupRecyclerView()
        readDatabase()
        readBackOnline()
        checkNetworkStatus()
        navigateBottomSheet()


        mainViewModel.pairMediatorLiveData.observe(viewLifecycleOwner) { (productsResponse, readProducts) ->
            handleReadDataErrors(binding.errorImageView, productsResponse, readProducts)
            handleReadDataErrors(binding.errorTextView, productsResponse, readProducts)

        }

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
        Log.e("ahmet", "data from api")
        mainViewModel.getProducts(productsViewModel.applyQueries())
        mainViewModel.productsResponse.observe(viewLifecycleOwner) { response ->
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
                    productsViewModel.networkStatus = status
                    productsViewModel.showNetworkStatus()
                    readDatabase()
                }
        }
    }

    private fun readBackOnline() {
        productsViewModel.readBackOnline.observe(viewLifecycleOwner) {
            productsViewModel.backOnline = it
        }
    }

    private fun showShimmerEffect() {
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerview.hideShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.products_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.queryHint = resources.getString(R.string.search_by_tag)
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }


    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.getSearchProducts(productsViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedProductsResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}