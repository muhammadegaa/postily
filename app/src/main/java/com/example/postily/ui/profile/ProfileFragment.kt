package com.example.postily.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postily.R
import com.example.postily.databinding.FragmentProfileBinding
import com.example.postily.ui.profile.contacts.Contact
import com.example.postily.ui.profile.contacts.ContactAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactList = listOf(
            Contact(R.drawable.sample_image, "user123", "Alice Smith"),
            Contact(R.drawable.sample_image, "jane_doe", "Jane Doe"),
            Contact(R.drawable.sample_image, "bob_johnson", "Bob Johnson")
        )

        val friendsRecyclerView: RecyclerView = view.findViewById(R.id.rvContact)
        val friendsAdapter = ContactAdapter(contactList)
        friendsRecyclerView.adapter = friendsAdapter
        friendsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}