package com.example.postily

import com.example.postily.model.tasks.Task
import com.example.postily.repository.TaskRepository
import com.example.postily.viewmodel.TaskViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: TaskViewModel

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = TaskViewModel(taskRepository)
    }

    @Test
    fun fetchTasks_updatesTasksWithDataFromRepository() = runTest {
        val mockTasks = listOf(Task(1, 1, "Task Title", false))
        coEvery { taskRepository.getTasks() } returns mockTasks
        coEvery { taskRepository.fetchTasksFromApi() } returns emptyList()

        viewModel.fetchTasks()

        advanceUntilIdle()

        assertEquals(mockTasks, viewModel.tasks.value)
        coVerify { taskRepository.getTasks() }
    }

    @Test
    fun fetchTasks_handlesErrorCorrectly() = runTest {
        coEvery { taskRepository.getTasks() } throws Exception("API Error")
        coEvery { taskRepository.fetchTasksFromApi() } throws Exception("API Error")

        viewModel.fetchTasks()

        advanceUntilIdle()

        assertTrue(viewModel.tasks.value.isEmpty())
    }

    @Test
    fun fetchTasks_filtersCompletedAndTodoTasksCorrectly() = runTest {
        val mockTasks = listOf(
            Task(1, 1, "Task 1", false),
            Task(2, 2, "Task 2", true)
        )
        coEvery { taskRepository.getTasks() } returns mockTasks
        coEvery { taskRepository.fetchTasksFromApi() } returns emptyList()

        viewModel.fetchTasks()

        advanceUntilIdle()

        assertEquals(1, viewModel.tasks.value.count { it.completed })
        assertEquals(1, viewModel.tasks.value.count { !it.completed })
    }
}
