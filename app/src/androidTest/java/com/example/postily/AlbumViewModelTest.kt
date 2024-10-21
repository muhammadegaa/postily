package com.example.postily

import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.repository.AlbumRepository
import com.example.postily.viewmodel.AlbumViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumViewModelTest {

    @MockK
    lateinit var albumRepository: AlbumRepository
    private lateinit var viewModel: AlbumViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Initialize MockK for all declared @MockK variables
        MockKAnnotations.init(this, relaxed = true)

        // Inject the testDispatcher for coroutines
        Dispatchers.setMain(testDispatcher)

        // Set up the ViewModel with the mocked repository
        viewModel = AlbumViewModel(albumRepository)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun fetchAlbums_updatesAlbumsWithDataFromRepository() = runTest {
        // Arrange: Mock the repository to return a list of albums
        val mockAlbums = listOf(Album(1, 1, "Album Title"))
        coEvery { albumRepository.getAlbums() } returns mockAlbums
        coEvery { albumRepository.getAlbumsFromFirestore() } returns emptyList()

        // Act: Call fetchAlbums on the ViewModel
        viewModel.fetchAlbums()

        // Execute pending coroutines
        advanceUntilIdle()

        // Assert: Verify that albums were updated with the mocked data
        assertEquals(mockAlbums, viewModel.albums.value)

        // Verify that the repository method was called
        coVerify { albumRepository.getAlbums() }
    }

    @Test
    fun fetchPhotos_updatesPhotosForASpecificAlbum() = runTest {
        // Arrange: Mock the repository to return photos for an album
        val albumId = 1
        val mockPhotos = listOf(Photo(1, albumId, "Photo Title", "url", "thumbnailUrl"))
        coEvery { albumRepository.getPhotos(albumId) } returns mockPhotos
        coEvery { albumRepository.getPhotosFromFirestore(albumId) } returns emptyList()

        // Act: Call fetchPhotos on the ViewModel
        viewModel.fetchPhotos(albumId)

        // Execute pending coroutines
        advanceUntilIdle()

        // Assert: Verify that photos were updated with the mocked data
        assertEquals(mockPhotos, viewModel.photos.value)

        // Verify that the repository method was called
        coVerify { albumRepository.getPhotos(albumId) }
    }

    @Test
    fun fetchPhotos_handlesErrorCorrectly() = runTest {
        // Arrange: Mock the repository to throw an exception
        val albumId = 1
        coEvery { albumRepository.getPhotos(albumId) } throws Exception("API Error")
        coEvery { albumRepository.getPhotosFromFirestore(albumId) } returns emptyList()

        // Act: Call fetchPhotos on the ViewModel
        viewModel.fetchPhotos(albumId)

        // Execute pending coroutines
        advanceUntilIdle()

        // Assert: Verify that photos are empty when an error occurs
        assertTrue(viewModel.photos.value.isEmpty())

        // Verify that the repository method was called
        coVerify { albumRepository.getPhotos(albumId) }
    }
}