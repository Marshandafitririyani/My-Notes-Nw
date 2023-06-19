package com.example.mynotes.data.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mynotes.R

class ViewBindingHelper {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["imageUrl"], requireAll = false)
        fun loadImageRecipe(view: ImageView, imageUrl: String?) {

            view.setImageDrawable(null)

            imageUrl?.let {
                Glide
                    .with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.picture)
                    .error(R.drawable.error)
                    .into(view)

            }

        }

    }

}