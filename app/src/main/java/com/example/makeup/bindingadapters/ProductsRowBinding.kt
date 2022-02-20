package com.example.makeup.bindingadapters



import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import coil.load
import com.example.makeup.R
import com.example.makeup.models.ProductsItem
import com.example.makeup.ui.fragments.products.ProductsFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class ProductsRowBinding {

    companion object{

        fun onProductClickListener(productRowLayout: ConstraintLayout, productsItem: ProductsItem){
            productRowLayout.setOnClickListener {
                try {
                    val action = ProductsFragmentDirections.actionProductsFragmentToDetailsActivity(productsItem)
                    productRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.e("onProductClickListener",e.toString())
                }
            }
        }


        fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_sad)
            }
        }


        fun parseHtml(textView: TextView, description: String?){
            description?.let {
                val desc = Jsoup.parse(it).text()
                textView.text = desc
            }
        }

    }

}