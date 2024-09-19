package com.example.postily.ui.feed.comment

import androidx.recyclerview.widget.RecyclerView
import com.example.postily.databinding.CommentItemBinding
import com.example.postily.ui.feed.Feed

class CommentViewHolder(
    private val binding: CommentItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindComment(comment: Comment) {
    binding.email.text = comment.email
    binding.commentTitle.text = comment.name
    binding.commentText.text = comment.body
}
}