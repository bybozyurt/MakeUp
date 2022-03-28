package com.example.makeup.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.R
import com.example.makeup.databinding.ProductsRowLayoutBinding
import com.example.makeup.data.models.Products
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.util.ProductsDiffUtil
import com.example.makeup.util.extensions.loadImageFromUrl
import com.example.makeup.util.extensions.onProductClickListener
import com.example.makeup.util.extensions.parseHtml

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    private var products = emptyList<ProductsItem>()
    private lateinit var rootView: View

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

        rootView = holder.itemView.rootView

        with(holder.binding) {
            with(currentProducts) {
                productsRowLayout.onProductClickListener(this)
                descriptionTextView.parseHtml(description)
                imageLink?.let { productImageView.loadImageFromUrl(it) }
                nameTextView.text = name
                updateColors(
                    tagList!!.contains(rootView.resources.getString(R.string.vegan)),
                    leafTextView,
                    leafImageView
                )
                updateColors(
                    tagList.contains(rootView.resources.getString(R.string.gluten_free)),
                    glutenFreeTextView,
                    glutenFreeImageView
                )

            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: Products) {
        val productsDiffUtil =
            ProductsDiffUtil(products, newData)
        val diffUtilResult = DiffUtil.calculateDiff(productsDiffUtil)
        products = newData
        diffUtilResult.dispatchUpdatesTo(this)
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
                    R.color.darkerGray
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


}