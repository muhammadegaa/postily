package com.example.postily

import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.repository.FeedRepository
import com.example.postily.viewmodel.FeedViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FeedViewModelTest {

    @MockK
    lateinit var feedRepository: FeedRepository

    lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = FeedViewModel(feedRepository)
    }

    @Test
    fun fetchPosts_updatesPostsWithDataFromRepository() = runTest {
        // Arrange: Mock the repository to return a list of posts
        val mockPosts = listOf(Post(userId = 1, id = 1, title = "Post Title", body = "Post Body"))
        coEvery { feedRepository.getPosts() } returns mockPosts

        // Act: Call fetchPosts on the ViewModel
        viewModel.fetchPosts()

        // Advance until the coroutine completes
        advanceUntilIdle()

        // Assert: Verify that posts were updated with the mocked data
        assertEquals(mockPosts, viewModel.posts.value)

        // Verify that the repository method was called
        coVerify { feedRepository.getPosts() }
    }

    @Test
    fun fetchComments_updatesCommentsWithDataFromRepository() = runTest {
        // Arrange: Mock the repository to return a list of comments
        val mockComments = listOf(
            Comment(postId = 1, id = 1, name = "Comment Body", email = "test@gmail.com", body = "test body")
        )
        coEvery { feedRepository.getComments(1) } returns mockComments

        // Act: Call fetchComments on the ViewModel
        viewModel.fetchComments(1)

        // Advance until the coroutine completes
        advanceUntilIdle()

        // Assert: Verify that comments were updated with the mocked data
        assertEquals(mockComments, viewModel.comments.value)
    }

    @Test
    fun fetchPosts_handlesErrorCorrectly() = runTest {
        // Arrange: Mock the repository to throw an exception
        coEvery { feedRepository.getPosts() } throws Exception("API Error")

        // Act: Call fetchPosts on the ViewModel
        viewModel.fetchPosts()

        // Advance until the coroutine completes
        advanceUntilIdle()

        // Assert: Verify that posts are empty when an error occurs
        assertTrue(viewModel.posts.value.isEmpty())
    }

    @Test
    fun fetchComments_handlesErrorCorrectly() = runTest {
        // Arrange: Mock the repository to throw an exception
        coEvery { feedRepository.getComments(1) } throws Exception("API Error")

        // Act: Call fetchComments on the ViewModel
        viewModel.fetchComments(1)

        // Advance until the coroutine completes
        advanceUntilIdle()

        // Assert: Verify that comments are empty when an error occurs
        assertTrue(viewModel.comments.value.isEmpty())
    }
}
