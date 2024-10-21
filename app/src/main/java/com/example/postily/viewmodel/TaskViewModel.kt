package com.example.postily.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.tasks.Task
import com.example.postily.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        fetchTasks()
    }

    internal fun fetchTasks() {
        viewModelScope.launch {
            try {
                val fetchedTasks = repository.getTasks()

                // If no tasks exist in Firestore, fetch them from API and save to Firestore
                if (fetchedTasks.isEmpty()) {
                    val apiTasks = repository.fetchTasksFromApi()
                    repository.saveTasksToFirestore(apiTasks)  // Save to Firestore
                    _tasks.value = apiTasks
                } else {
                    _tasks.value = fetchedTasks  // Use tasks from Firestore
                }
            } catch (e: Exception) {
                _tasks.value = emptyList()  // Handle any errors
            }
        }
    }
}

