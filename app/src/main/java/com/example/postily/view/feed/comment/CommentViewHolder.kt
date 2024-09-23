package com.example.postily.view.feed.comment

import androidx.recyclerview.widget.RecyclerView
import com.example.postily.databinding.CommentItemBinding
import com.example.postily.model.feed.Comment

class CommentViewHolder(
    private val binding: CommentItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindComment(comment: Comment) {
    binding.email.text = comment.email
    binding.commentTitle.text = comment.name
    binding.commentText.text = comment.body
}
}