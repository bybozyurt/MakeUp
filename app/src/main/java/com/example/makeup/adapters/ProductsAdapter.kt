package com.example.makeup.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.bindingadapters.ProductsRowBinding
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.applyVeganColor
import com.example.makeup.bindingadapters.ProductsRowBinding.Companion.loadImageFromUrl
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
                parseHtml(descriptionTextView, description)
                loadImageFromUrl(productImageView, imageLink)
                nameTextView.text = name
                applyVeganColor(leafImageView, tagList!!.contains("Vegan"))
                applyVeganColor(leafTextView, tagList.contains("Vegan"))
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: Products){
        val recipesDiffUtil =
            ProductsDiffUtil(products, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        products = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}