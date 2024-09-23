package com.example.postily.view.albums.image

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postily.databinding.ImageItemBinding

class ImageViewHolder(private val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: com.example.postily.model.albums.Image) {
        Glide.with(itemView.context)
            .load(image.imageUrl)
            .into(binding.imageView)}
}