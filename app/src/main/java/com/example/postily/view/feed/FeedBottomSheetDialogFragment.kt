package com.example.postily.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.R
import com.example.postily.databinding.FeedDetailsBinding
import com.example.postily.model.feed.Feed
import com.example.postily.network.CommentApiService
import com.example.postily.view.feed.comment.CommentsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FeedBottomSheetDialogFragment(private val feed: Feed) : BottomSheetDialogFragment() {

    private var _binding: FeedDetailsBinding? =null
    private val binding get() = _binding!!

    private lateinit var commentApiService: CommentApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feedDetailImage.setImageResource(feed.cover)
        binding.feedDetailTitle.text = feed.title
        binding.feedDetailDescription.text = feed.body

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        commentApiService =retrofit.create(CommentApiService::class.java)

        val btnBack = view.findViewById<ImageView>(R.id.btnBSBackFeed)
        btnBack.setOnClickListener {
            dismiss()
        }

        fetchComments(view)
    }

    private fun fetchComments(view: View) {
        lifecycleScope.launch {
            try {
                val comments = commentApiService.getComments()
                val filteredComments = comments.filter { it.postId == feed.id }
                val commentsRecyclerView = view.findViewById<RecyclerView>(R.id.comments_recycler_view)
                commentsRecyclerView.adapter = CommentsAdapter(filteredComments)
            } catch (e: Exception) {
                // Handle error, e.g., show an error message or a placeholder
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}