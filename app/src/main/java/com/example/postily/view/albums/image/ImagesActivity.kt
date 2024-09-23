package com.example.postily.view.albums.image

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.postily.R
import com.example.postily.databinding.ActivityImagesBinding
import com.example.postily.model.albums.Image

class ImagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagesBinding
    private lateinit var imagesAdapter: ImagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get album title from intent
        val albumTitle = intent.getStringExtra("album_id")
        imagesAdapter = ImagesAdapter(getImagesForAlbum(albumTitle))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up RecyclerView
        imagesAdapter = ImagesAdapter(getImagesForAlbum(albumTitle))
        binding.imagesRecyclerView.adapter = imagesAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(this, 3)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun getImagesForAlbum(albumTitle: String?): MutableList<Image> {
        val images = mutableListOf<Image>()
        if (albumTitle != null) {
            for (i in 1..10) {
                images.add(Image("Image $i in $albumTitle", "https://picsum.photos/200"))
            }
        }
        return images
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}