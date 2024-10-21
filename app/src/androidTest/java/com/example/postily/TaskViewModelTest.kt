package com.example.postily

import com.example.postily.model.tasks.Task
import com.example.postily.repository.TaskRepository
import com.example.postily.viewmodel.TaskViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskViewModelTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    lateinit var viewModel: TaskViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = TaskViewModel(taskRepository)
    }

    @Test
    fun fetchTasks_updatesTasksWithDataFromRepository() = runTest {
        val mockTasks = listOf(Task(1, 1, "Task Title", false))
        coEvery { taskRepository.getTasks() } returns mockTasks

        viewModel.fetchTasks()

        // Assert that the tasks are updated with the mocked data
        assertEquals(mockTasks, viewModel.tasks.value)

        // Verify that the repository method was called
        coVerify { taskRepository.getTasks() }
    }

    @Test
    fun fetchTasks_handlesErrorCorrectly() = runTest {
        coEvery { taskRepository.getTasks() } throws Exception("API Error")

        viewModel.fetchTasks()

        // Assert that tasks are empty when an error occurs
        assertTrue(viewModel.tasks.value.isEmpty())
    }

    @Test
    fun fetchTasks_filtersCompletedAndTodoTasksCorrectly() = runTest {
        val mockTasks = listOf(
            Task(1, 1, "Task 1", false),
            Task(2, 2, "Task 2", true)
        )
        coEvery { taskRepository.getTasks() } returns mockTasks

        viewModel.fetchTasks()

        // Assert that completed and to-do tasks are filtered correctly
        assertEquals(1, viewModel.tasks.value.count { it.completed })
        assertEquals(1, viewModel.tasks.value.count { !it.completed })
    }
}
