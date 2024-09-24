package com.example.postily

import com.example.postily.model.feed.CommentResponse
import com.example.postily.model.feed.PostResponse
import com.example.postily.network.PostApiService
import com.example.postily.repository.FeedRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FeedRepositoryTest {

    @Mock
    lateinit var postApiService: PostApiService

    private lateinit var feedRepository: FeedRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        feedRepository = FeedRepository(postApiService)
    }

    @Test
    fun `test getPosts success`() = runBlockingTest {
        val mockPosts = listOf(
            PostResponse(1,1,"Title 1","Body 1"),
            PostResponse(1,2,"Title 2","Body 2")
        )

//        whenever(postApiService.getPosts()).thenReturn(mockPosts)

        val result = feedRepository.getPosts()

        assertEquals(mockPosts, result)
        verify(postApiService).getPosts()
    }

    @Test
    fun `test getComments success`() = runBlockingTest {
        val mockComments = listOf(
            CommentResponse(1,1,"Comment 1", "email1@test.com","Body 1"),
            CommentResponse(1,2,"Comment 2", "email2@test.com","Body 2")
        )

//        whenever(postApiService.getComments(1)).thenReturn(mockComments)

        val result = feedRepository.getComments(1)

        assertEquals(mockComments, result)
        verify(postApiService).getComments(1)
    }
}