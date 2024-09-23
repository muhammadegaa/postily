package com.example.postily.view.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postily.databinding.FragmentTasksBinding
import com.example.postily.viewmodel.TasksViewModel

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel: TasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val ongoingTasks = tasks.filter { !it.completed }
            val completedTasks = tasks.filter { it.completed }

            val todoAdapter = TasksAdapter(ongoingTasks)
            binding.rvTodo.adapter = todoAdapter
            binding.rvTodo.layoutManager = LinearLayoutManager(context)

            val completedAdapter = TasksAdapter(completedTasks)
            binding.rvCompleted.adapter = completedAdapter
            binding.rvCompleted.layoutManager = LinearLayoutManager(context)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}