package com.example.postily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.tasks.Task
import com.example.postily.network.TodoApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TasksViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            try {
                val tasks = getTasks()
                _tasks.value = tasks
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun getTasks(): List<Task> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val todoApi = retrofit.create(TodoApi::class.java)
        return todoApi.getTodos()
    }
}