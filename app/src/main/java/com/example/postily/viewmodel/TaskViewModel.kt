package com.example.postily.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.tasks.Task
import com.example.postily.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    var tasks = mutableStateListOf<Task>()
        private set

    init {
        fetchTasks()
    }

    // Fetch tasks from the repository
    private fun fetchTasks() {
        viewModelScope.launch {
            val fetchedTasks = repository.getTasks()
            tasks.addAll(fetchedTasks)
        }
    }
}
