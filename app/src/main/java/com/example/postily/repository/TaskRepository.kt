package com.example.postily.repository

import com.example.postily.model.tasks.Task
import com.example.postily.network.RetrofitInstance
import com.example.postily.network.TaskApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiService: TaskApiService,
    private val firestore: FirebaseFirestore
) {

    companion object {
        const val TASKS_COLLECTION = "tasks"
    }

    // Fetch tasks from Firestore
    suspend fun getTasks(): List<Task> {
        return try {
            val snapshot = firestore.collection(TASKS_COLLECTION).get().await()
            snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
        } catch (e: Exception) {
            // Handle the exception, and return an empty list in case of an error
            emptyList()
        }
    }

    // Save tasks fetched from the API to Firestore
    suspend fun saveTasksToFirestore(tasks: List<Task>) {
        try {
            val batch = firestore.batch()
            tasks.forEach { task ->
                val document = firestore.collection(TASKS_COLLECTION).document(task.id.toString())
                batch.set(document, task)
            }
            batch.commit().await()
        } catch (e: Exception) {
            // Handle any errors when saving to Firestore
        }
    }

    // Fetch tasks from the API
    suspend fun fetchTasksFromApi(): List<Task> {
        return apiService.getTasks()
    }
}