package com.example.makeup.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makeup.util.extensions.showSnackBar
import com.example.makeup.R
import com.example.makeup.adapters.FavoriteProductsAdapter
import com.example.makeup.databinding.FragmentFavoriteProductsBinding
import com.example.makeup.util.extensions.setVisibility
import com.example.makeup.util.extensions.showCustomSnackBar
import com.example.makeup.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteProductsAdapter by lazy { FavoriteProductsAdapter(requireActivity(), mainViewModel) }

    private var _binding: FragmentFavoriteProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteProductsBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        setupRecyclerView()

        mainViewModel.readFavoriteProducts.observe(viewLifecycleOwner) { favoritesEntity ->
            mAdapter.setData(favoritesEntity)

            binding.noDataImageView.setVisibility(favoritesEntity, mAdapter)
            binding.noDataTextView.setVisibility(favoritesEntity, mAdapter)

        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_products_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorite_products_menu)
        {
            mainViewModel.deleteAllFavoriteProducts()
            requireActivity().showCustomSnackBar("All Products Removed.", binding.root)
        }
        return super.onOptionsItemSelected(item)

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
        mAdapter.clearContextualActionMode()
    }


}