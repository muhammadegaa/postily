package com.example.postily.ui.feed

var feedList = mutableListOf<Feed>()

val FEED_ID_EXTRA = "feedExtra"

class Feed(
    var cover: Int,
    var title: String,
    var body: String,
    val id: Int? = feedList.size
)