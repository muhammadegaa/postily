package com.example.postily.view.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postily.R
import com.example.postily.databinding.CardFeedBinding
import com.example.postily.model.feed.Feed

class CardAdapter(
    private val feeds: List<Feed>,
    private val clickListener: FeedClickListener
)
    : RecyclerView.Adapter<FeedViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = CardFeedBinding.inflate(from, parent, false)

        return FeedViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int)
    {
        holder.bindFeed(feeds[position])
    }

    override fun getItemCount(): Int = feeds.size

}