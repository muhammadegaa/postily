package com.example.postily.ui.albums.image

import android.media.Image
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postily.databinding.ImageItemBinding

class ImageViewHolder(private val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: com.example.postily.ui.albums.image.Image) {
        Glide.with(itemView.context)
            .load(image.imageUrl)
            .into(binding.imageView)}
}