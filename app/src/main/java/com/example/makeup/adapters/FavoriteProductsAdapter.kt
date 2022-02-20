package com.example.makeup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.R
import com.example.makeup.bindingadapters.ProductsRowBinding
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.loadImageFromUrl
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.parseHtml
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.databinding.FavoriteProductsRowLayoutBinding
import com.example.makeup.ui.fragments.favorites.FavoriteProductsFragmentDirections
import com.example.makeup.util.ProductsDiffUtil
import com.example.makeup.viewmodels.MainViewModel

class FavoriteProductsAdapter(
) : RecyclerView.Adapter<FavoriteProductsAdapter.MyViewHolder>() {

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

        with(holder.binding) {
            with(favoriteProducts[position].productsItem) {
                loadImageFromUrl(favoriteProductImageView, imageLink)
                parseHtml(favoriteDescriptionTextView, description)
                favoriteNameTextView.text = name.toString()
                applyColor(favoriteLeafImageView, tagList!!.contains("Vegan"))
                applyColor(favoriteLeafTextView, tagList.contains("Vegan"))
                applyColor(favoriteNaturalImageView, tagList.contains("Natural"))
                applyColor(favoriteNaturalTextView, tagList.contains("Natural"))
                applyColor(favoriteOrganicImageView, tagList.contains("Organic"))
                applyColor(favoriteOrganicTextView, tagList.contains("Organic"))

                //Single Click Listener
                favoriteProductsRowLayout.setOnClickListener {

                    val action =
                        FavoriteProductsFragmentDirections.actionFavoriteProductsFragmentToDetailsActivity(
                            this
                        )
                    holder.itemView.findNavController().navigate(action)
                }
            }

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

    private fun applyColor(view: View, tag: Boolean) {
        if (tag) {
            when (view) {
                is TextView -> {
                    view.setTextColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )

                }
                is ImageView -> {
                    view.setColorFilter(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )
                }
            }
        }
    }


}