package com.example.postily.view.feed

import androidx.recyclerview.widget.RecyclerView
import com.example.postily.databinding.CardFeedBinding
import com.example.postily.model.feed.FeedEntity

class FeedViewHolder(
    private val cardFeedBinding: CardFeedBinding,
    private val clickListener: FeedClickListener
) : RecyclerView.ViewHolder(cardFeedBinding.root)
{
    fun bindFeed(feed: FeedEntity)
    {
        cardFeedBinding.cover.setImageResource(feed.cover)
        cardFeedBinding.title.text = feed.title
        cardFeedBinding.description.text = feed.body

        cardFeedBinding.cardView.setOnClickListener{
            clickListener.onFeedClick(feed)
        }
    }
}