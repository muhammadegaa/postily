package com.example.postily.ui.profile.contacts

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.R

class ContactAdapter(private val friends: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Get references to the views in your item layout
        val profileImageView: ImageView = itemView.findViewById(R.id.profile_image)
        val usernameTextView: TextView = itemView.findViewById(R.id.username)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val chevronButton: ImageButton = itemView.findViewById(R.id.chevron_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact, parent, false) // Use your item layout
        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        holder.profileImageView.setImageResource(R.drawable.sample_image)
        holder.usernameTextView.text = friend.username
        holder.nameTextView.text = friend.name

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ContactDetails::class.java)
            // Pass any data you need to the ContactDetails activity
            intent.putExtra("username", friend.username)
            intent.putExtra("name", friend.name)
            // ... other data

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = friends.size
}