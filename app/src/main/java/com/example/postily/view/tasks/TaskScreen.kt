package com.example.postily.view.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.model.tasks.Task
import com.example.postily.viewmodel.TaskViewModel

@Composable
fun TaskScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val tasks = viewModel.tasks

    Column {
        Text(text = "Ongoing Tasks")
        LazyColumn {
            items(tasks.filter { !it.completed }) { task ->
                TaskItem(task)
            }
        }
        Text(text = "Completed Tasks")
        LazyColumn {
            items(tasks.filter { it.completed }) { task ->
                TaskItem(task)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Text(text = task.title)
}