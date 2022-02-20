package com.example.makeup.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooder.util.extensions.showSnackBar
import com.example.makeup.R
import com.example.makeup.adapters.FavoriteProductsAdapter
import com.example.makeup.bindingadapters.FavoriteProductsBinding.Companion.setVisibility
import com.example.makeup.databinding.FragmentFavoriteProductsBinding
import com.example.makeup.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteProductsAdapter by lazy { FavoriteProductsAdapter() }


    private var _binding: FragmentFavoriteProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteProductsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        setupRecyclerView()

        mainViewModel.readFavoriteProducts.observe(viewLifecycleOwner) { favoritesEntity ->
            mAdapter.setData(favoritesEntity)
            setVisibility(binding.noDataImageView, favoritesEntity, mAdapter)
            setVisibility(binding.noDataTextView, favoritesEntity, mAdapter)

        }


        return binding.root
    }


    private fun setupRecyclerView() {
        with(binding.favoriteProductsRecyclerView){
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}