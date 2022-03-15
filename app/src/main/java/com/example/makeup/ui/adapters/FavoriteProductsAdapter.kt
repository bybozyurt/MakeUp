package com.example.makeup.ui.adapters

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.R
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.databinding.FavoriteProductsRowLayoutBinding
import com.example.makeup.ui.favorites.FavoriteProductsFragmentDirections
import com.example.makeup.util.Constants.Companion.DELETE_ICON
import com.example.makeup.util.ProductsDiffUtil
import com.example.makeup.util.extensions.loadImageFromUrl
import com.example.makeup.util.extensions.parseHtml
import com.example.makeup.util.extensions.showCustomSnackBar
import com.example.makeup.ui.viewmodels.MainViewModel

class FavoriteProductsAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteProductsAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View
    private var selectedProducts = arrayListOf<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()

    private var favoriteProducts = emptyList<FavoritesEntity>()

    class MyViewHolder(val binding: FavoriteProductsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FavoriteProductsRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        //global
        rootView = holder.itemView.rootView

        saveItemStateOnScroll(favoriteProducts[position], holder)

        with(holder.binding) {
            with(favoriteProducts[position].productsItem) {
                imageLink?.let { favoriteProductImageView.loadImageFromUrl(it) }
                favoriteDescriptionTextView.parseHtml(description)
                favoriteNameTextView.text = name.toString()

                updateColors(
                    tagList!!.contains(rootView.resources.getString(R.string.vegan)),
                    favoriteLeafTextView,
                    favoriteLeafImageView
                )
                updateColors(
                    tagList.contains(rootView.resources.getString(R.string.gluten_free)),
                    favoriteGlutenFreeTextView,
                    favoriteGlutenFreeImageView
                )

                //Single Click Listener
                favoriteProductsRowLayout.setOnClickListener {
                    if (multiSelection) {
                        applySelection(holder, favoriteProducts[position])
                    } else {
                        val action =
                            FavoriteProductsFragmentDirections.actionFavoriteProductsFragmentToDetailsActivity(
                                this
                            )
                        holder.itemView.findNavController().navigate(action)
                    }

                }
            }

            // Long Click Listener
            favoriteProductsRowLayout.setOnLongClickListener {
                if (multiSelection.not()) {
                    multiSelection = true
                    requireActivity.startActionMode(this@FavoriteProductsAdapter)
                    applySelection(holder, favoriteProducts[position])
                    true
                } else {
                    applySelection(holder, favoriteProducts[position])
                    true
                }
            }

        }

    }

    //triggered every time scroll up and down
    private fun saveItemStateOnScroll(currentProducts: FavoritesEntity, holder: MyViewHolder) {
        if (selectedProducts.contains(currentProducts)) {
            changeProductsStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        } else {
            changeProductsStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }

    }

    private fun applySelection(holder: MyViewHolder, currentProducts: FavoritesEntity) {
        if (selectedProducts.contains(currentProducts)) {
            selectedProducts.remove(currentProducts)
            changeProductsStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedProducts.add(currentProducts)
            changeProductsStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeProductsStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        with(holder.binding) {
            favoriteProductsRowLayout.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity,
                    backgroundColor
                )
            )
            favoriteRowCardView.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
        }
    }

    private fun applyActionModeTitle() {
        when (selectedProducts.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title = "${selectedProducts.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedProducts.size} items selected"
            }
        }
    }


    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        //global
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_products_menu) {
            selectedProducts.forEach {
                mainViewModel.deleteFavoriteProduct(it)
                rootView.context.applicationContext.showCustomSnackBar(
                    "${selectedProducts.size} item/s deleted",
                    rootView,
                    DELETE_ICON
                )
            }
            multiSelection = false
            selectedProducts.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeProductsStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedProducts.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(
                ContextCompat.getColor(
                    rootView.context.applicationContext,
                    R.color.green
                )
            )
            textView.setTextColor(
                ContextCompat.getColor(
                    rootView.context.applicationContext,
                    R.color.green
                )
            )
        } else {
            imageView.setColorFilter(
                ContextCompat.getColor(
                    rootView.context.applicationContext,
                    R.color.darkGray
                )
            )
            textView.setTextColor(
                ContextCompat.getColor(
                    rootView.context.applicationContext,
                    R.color.darkGray
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return favoriteProducts.size
    }

    fun setData(newFavoriteProducts: List<FavoritesEntity>) {
        val favoriteProductsDiffUtil =
            ProductsDiffUtil(favoriteProducts, newFavoriteProducts)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteProductsDiffUtil)
        favoriteProducts = newFavoriteProducts
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

}