package com.example.makeup.ui.favorites

import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ab.makeup.R
import com.ab.makeup.databinding.FragmentFavoriteProductsBinding
import com.example.makeup.ui.adapters.FavoriteProductsAdapter
import com.example.makeup.ui.base.BaseBindingFragment
import com.example.makeup.util.extensions.setVisibility
import com.example.makeup.util.extensions.showCustomSnackBar
import com.example.makeup.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsFragment : BaseBindingFragment<FragmentFavoriteProductsBinding>() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteProductsAdapter by lazy {
        FavoriteProductsAdapter(
            requireActivity(),
            mainViewModel
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_products_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorite_products_menu) {
            mainViewModel.deleteAllFavoriteProducts()
            requireActivity().showCustomSnackBar(
                getString(R.string.all_products_removed),
                mBinding?.root,
                getString(R.string.icon_delete)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        mBinding?.let {
            with(it.favoriteProductsRecyclerView) {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(requireContext())
                itemAnimator = null
            }
        }
    }

    fun readFavoriteProductsObserve() {
        mainViewModel.readFavoriteProducts.observe(viewLifecycleOwner) { favoritesEntity ->
            mAdapter.setData(favoritesEntity)
            mBinding?.let {
                with(it) {
                    noDataImageView.setVisibility(favoritesEntity, mAdapter)
                    noDataTextView.setVisibility(favoritesEntity, mAdapter)
                }
            }
        }
    }

    override fun getContentLayoutResId() = R.layout.fragment_favorite_products

    override fun populateUI() {
        setHasOptionsMenu(true)
        setupRecyclerView()
        readFavoriteProductsObserve()
    }

    override fun onDestView() {
        mBinding = null
        mAdapter.clearContextualActionMode()
    }
}