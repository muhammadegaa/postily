package com.example.postily.view.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postily.model.tasks.Task
import com.example.postily.viewmodel.TaskViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    val tasks by viewModel.tasks.collectAsState()

    // Separate tasks into "To-Do" and "Completed"
    val toDoTasks = tasks.filter { !it.completed }
    val completedTasks = tasks.filter { it.completed }

    Column(modifier = Modifier.fillMaxSize()) {
        // Scrollable To-Do list with sticky header
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            stickyHeader {
                Text(
                    text = "To-Do",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // To keep the header visible with background
                        .padding(8.dp)
                )
            }
            items(toDoTasks) { task ->
                TaskItem(task = task, isCompleted = false)
            }
        }

        // Fixed "Completed" header and list
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Completed",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.4f) // Limit height for completed tasks list
            ) {
                items(completedTasks) { task ->
                    TaskItem(task = task, isCompleted = true)
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, isCompleted: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = if (isCompleted) {
                    androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.LineThrough // Strikethrough for completed tasks
                    )
                } else {
                    androidx.compose.material3.MaterialTheme.typography.bodyLarge
                }
            )
        }
    }
}