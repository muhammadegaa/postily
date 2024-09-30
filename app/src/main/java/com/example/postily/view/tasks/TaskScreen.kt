package com.example.postily.view.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postily.model.tasks.Task
import com.example.postily.viewmodel.TaskViewModel

@Composable
fun TaskScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    val tasks = viewModel.tasks.collectAsState().value

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "TO-DO",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn {
            items(tasks.filter { !it.completed }) { task ->
                TaskItem(task)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "COMPLETED TASKS",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn {
            items(tasks.filter { it.completed }) { task ->
                TaskItem(task)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp) // This is the correct way to set elevation in Material3
    ) {
        Text(
            text = task.title,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
