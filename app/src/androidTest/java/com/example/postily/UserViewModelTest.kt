package com.example.postily

import com.example.postily.model.profile.Address
import com.example.postily.model.profile.Company
import com.example.postily.model.profile.Geo
import com.example.postily.model.profile.User
import com.example.postily.repository.UserRepository
import com.example.postily.viewmodel.UserViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var userRepository: UserRepository

    lateinit var viewModel: UserViewModel

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = UserViewModel(userRepository)
    }

    @Test
    fun fetchUser_updates_user_with_data_from_repository() = runTest {
        val mockUser = User(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com",
            address = Address("Street", "Suite", "City", "Zip", Geo("0", "0")),
            phone = "123-456-7890",
            website = "website",
            company = Company("Company", "CatchPhrase", "BS")
        )

        coEvery { userRepository.getUser(1) } returns mockUser

        viewModel.fetchUser(1)

        assertEquals(mockUser, viewModel.user.value)
    }

    @Test
    fun fetchFriends_updates_friends_with_data_from_repository() = runTest {
        val mockFriends = listOf(
            User(
                id = 2,
                name = "Friend",
                username = "friend",
                email = "friend@example.com",
                address = Address("Street", "Suite", "City", "Zip", Geo("0", "0")),
                phone = "123-456-7890",
                website = "website",
                company = Company("Company", "CatchPhrase", "BS")
            )
        )

        coEvery { userRepository.getFriends() } returns mockFriends

        viewModel.fetchFriends()

        assertEquals(mockFriends, viewModel.friends.value)
    }

    @Test
    fun getFriendById_returns_correct_friend() = runTest {
        val mockFriends = listOf(
            User(
                id = 2,
                name = "Friend 1",
                username = "friend1",
                email = "friend1@example.com",
                address = Address("Street", "Suite", "City", "Zip", Geo("0", "0")),
                phone = "123-456-7890",
                website = "website",
                company = Company("Company", "CatchPhrase", "BS")
            ),
            User(
                id = 3,
                name = "Friend 2",
                username = "friend2",
                email = "friend2@example.com",
                address = Address("Street", "Suite", "City", "Zip", Geo("0", "0")),
                phone = "123-456-7890",
                website = "website",
                company = Company("Company", "CatchPhrase", "BS")
            )
        )

        coEvery { userRepository.getFriends() } returns mockFriends

        viewModel.fetchFriends()

        assertEquals("Friend 2", viewModel.getFriendById(3)?.name)
    }
}
