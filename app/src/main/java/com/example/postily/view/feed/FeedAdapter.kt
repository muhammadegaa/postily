package com.example.postily.view.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.databinding.CardFeedBinding
import com.example.postily.model.feed.FeedEntity
import com.example.postily.model.feed.Post
import com.example.postily.model.feed.PostResponse

//class FeedAdapter(
//    private val feeds: List<FeedEntity>,
//    private val clickListener: FeedClickListener
//)
//    : RecyclerView.Adapter<FeedViewHolder>()
//{
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder
//    {
//        val from = LayoutInflater.from(parent.context)
//        val binding = CardFeedBinding.inflate(from, parent, false)
//
//        return FeedViewHolder(binding, clickListener)
//    }
//
//    override fun onBindViewHolder(holder: FeedViewHolder, position: Int)
//    {
//        holder.bindFeed(feeds[position])
//    }
//
//    override fun getItemCount(): Int = feeds.size
//
//}

class FeedAdapter(
    private val onClick: (PostResponse) -> Unit
) : ListAdapter<PostResponse, FeedAdapter.FeedViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = CardFeedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
       val post = getItem(position)
        holder.bind(post, onClick)
    }

    class FeedViewHolder(private val binding: CardFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostResponse, onClick: (PostResponse) -> Unit) {
            binding.title.text = post.title
            binding.description.text = post.body
            binding.root.setOnClickListener { onClick(post) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PostResponse>() {
        override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem == newItem
        }

    }
}