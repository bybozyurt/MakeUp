package com.example.makeup.bindingadapters



import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.example.makeup.R
import org.jsoup.Jsoup

class ProductsRowBinding {

    companion object{



        fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_sad)
            }
        }


        fun applyVeganColor(view: View, vegan: Boolean){
            if (vegan){
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

        fun parseHtml(textView: TextView, description: String?){
            description?.let {
                val desc = Jsoup.parse(it).text()
                textView.text = desc
            }
        }

    }

}