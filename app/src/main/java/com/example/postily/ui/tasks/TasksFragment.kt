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
import com.example.postily.databinding.FragmentTasksBinding
import com.example.postily.ui.profile.contacts.ContactAdapter

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tasksViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val ongoingTasks = listOf(
            Task("Ongoing Task 1", false),
            Task("Ongoing Task 2", false),
            Task("Ongoing Task 3", false)
        )

        val completedTasks = listOf(
            Task("Completed Task 1", true),
            Task("Completed Task 2", true),
            Task("Completed Task 3", true)
        )


        val todoRecyclerView: RecyclerView = binding.rvTodo
        todoRecyclerView.adapter = TasksAdapter(ongoingTasks)
        todoRecyclerView.layoutManager = LinearLayoutManager(context)

        val completedRecyclerView: RecyclerView = binding.rvCompleted
        completedRecyclerView.adapter = TasksAdapter(completedTasks)
        completedRecyclerView.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}