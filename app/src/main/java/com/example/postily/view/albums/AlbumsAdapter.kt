package com.example.postily.view.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.databinding.AlbumItemBinding
import com.example.postily.model.albums.Album

class AlbumsAdapter(private var albums: List<Album>, private val onAlbumClick: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.tvAlbumTitle.text = album.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(albums[position])
        holder.itemView.setOnClickListener { onAlbumClick(album) }
    }

    override fun getItemCount(): Int = albums.size
}