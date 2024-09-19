package com.example.postily.ui.profile.contacts

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.postily.R

class ContactDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        val profileImageView: ImageView = findViewById(R.id.profile_image_details)
        val usernameTextView: TextView = findViewById(R.id.username_details)
        val nameTextView: TextView = findViewById(R.id.name_details)

        // Retrieve data from intent
        val username = intent.getStringExtra("username")
        val name = intent.getStringExtra("name")
        val profileImage = intent.getIntExtra("profileImage", 0)

        // Set data to views
        usernameTextView.text = username
        nameTextView.text = name

        Glide.with(this)
            .load(profileImage)
            .into(profileImageView)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}