package com.example.postily.ui.albums

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.databinding.FragmentAlbumsBinding
import com.example.postily.ui.albums.image.Image
import com.example.postily.ui.albums.image.ImagesActivity

class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private lateinit var recyclerView: RecyclerView
    
    // This property is only valid betweenonCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumsAdapter = AlbumsAdapter(getAlbums()){ album ->
            val intent = Intent(context, ImagesActivity::class.java)
            intent.putExtra("album_id", album.title)
            startActivity(intent)
        }
        binding.albumsRecyclerView.adapter = albumsAdapter
        
    }

    private fun getAlbums(): List<Album> {
        val albums = mutableListOf<Album>()
        for (i in 1..10) {
            albums.add(Album("Album Number $i"))
        }
        return albums
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}