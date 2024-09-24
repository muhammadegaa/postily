package com.example.postily.view.feed

import com.example.postily.model.feed.FeedEntity


interface FeedClickListener {
        fun onFeedClick(feed: FeedEntity)
}