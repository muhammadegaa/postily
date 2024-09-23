package com.example.postily.view.feed

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postily.databinding.CardFeedBinding
import com.example.postily.model.feed.Feed

class FeedViewHolder(
    private val cardFeedBinding: CardFeedBinding,
    private val clickListener: FeedClickListener
) : RecyclerView.ViewHolder(cardFeedBinding.root)
{
    fun bindFeed(feed: Feed)
    {
        cardFeedBinding.cover.setImageResource(feed.cover)
        cardFeedBinding.title.text = feed.title
        cardFeedBinding.description.text = feed.body

        cardFeedBinding.cardView.setOnClickListener{
            clickListener.onFeedClick(feed)
        }
    }
}