package com.example.postily.model.feed

var feedList = mutableListOf<FeedEntity>()

val FEED_ID_EXTRA = "feedExtra"

data class FeedEntity(
    var cover: Int,
    var title: String,
    var body: String,
    val id: Int? = feedList.size
)