package com.example.makeup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.R
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.loadImageFromUrl
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.onProductClickListener
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.parseHtml
import com.example.makeup.databinding.ProductsRowLayoutBinding
import com.example.makeup.models.Products
import com.example.makeup.models.ProductsItem
import com.example.makeup.util.ProductsDiffUtil

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    private var products = emptyList<ProductsItem>()

    class MyViewHolder(val binding: ProductsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = ProductsRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProducts = products[position]

        with(holder.binding){
            with(currentProducts){
                onProductClickListener(productsRowLayout, this)
                parseHtml(descriptionTextView, description)
                loadImageFromUrl(productImageView, imageLink)
                nameTextView.text = name
                applyColor(leafImageView, tagList!!.contains("Vegan"))
                applyColor(leafTextView, tagList.contains("Vegan"))
                applyColor(naturalImageView, tagList.contains("Natural"))
                applyColor(naturalTextView, tagList.contains("Natural"))
                applyColor(organicImageView, tagList.contains("Organic"))
                applyColor(organicTextView, tagList.contains("Organic"))
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: Products){
        val productsDiffUtil =
            ProductsDiffUtil(products, newData)
        val diffUtilResult = DiffUtil.calculateDiff(productsDiffUtil)
        products = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applyColor(view: View, tag: Boolean){
        if (tag){
            when(view){
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