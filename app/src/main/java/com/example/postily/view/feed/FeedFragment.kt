package com.example.postily.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.R
import com.example.postily.databinding.FragmentFeedBinding
import com.example.postily.model.feed.FeedEntity
import com.example.postily.network.PostApiService
import com.example.postily.viewmodel.FeedViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class FeedFragment : Fragment(), FeedClickListener {
//
//    private var _binding: FragmentFeedBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var postApiService: PostApiService
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentFeedBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        recyclerView = binding.rvFeed
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        postApiService = retrofit.create(PostApiService::class.java)
//
//        fetchPosts()
//
//        return root
//    }
//
//    override fun onFeedClick(feed: FeedEntity) {
//        val bottomSheet = FeedBottomSheetDialogFragment(feed)
//        bottomSheet.show(childFragmentManager, "FeedBottomSheet")
//    }
//
//    private fun fetchPosts() {
//        lifecycleScope.launch {
//            try {
//                val posts = postApiService.getPosts()
//                val adapter = CardAdapter(posts, this@FeedFragment) // No need to pass CoroutineScope here
//                recyclerView.adapter = adapter
//            } catch (e: Exception) {
//                // Handle error, e.g., show an error message
//            }
//        }
//    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private val viewModel: FeedViewModel by viewModels()

    private lateinit var adapter: FeedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFeedBinding.bind(view)

        adapter = FeedAdapter { post ->
            viewModel.fetchComments(post.id)
        }

        binding.rvFeed.adapter = adapter

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchPosts()
    }
}