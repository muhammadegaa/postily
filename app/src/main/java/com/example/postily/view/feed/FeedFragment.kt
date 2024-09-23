package com.example.postily.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.network.PostApiService
import com.example.postily.databinding.FragmentFeedBinding
import com.example.postily.model.feed.Feed
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FeedFragment : Fragment(), FeedClickListener {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var postApiService: PostApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.rvFeed
        recyclerView.layoutManager = LinearLayoutManager(context)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postApiService = retrofit.create(PostApiService::class.java)

        fetchPosts()

        return root
    }

    override fun onFeedClick(feed: Feed) {
        val bottomSheet = FeedBottomSheetDialogFragment(feed)
        bottomSheet.show(childFragmentManager, "FeedBottomSheet")
    }

    private fun fetchPosts() {
        lifecycleScope.launch {
            try {
                val posts = postApiService.getPosts()
                val adapter = CardAdapter(posts, this@FeedFragment) // No need to pass CoroutineScope here
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                // Handle error, e.g., show an error message
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}