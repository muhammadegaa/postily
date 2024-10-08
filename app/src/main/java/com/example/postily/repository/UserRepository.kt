package com.example.postily.repository

import com.example.postily.model.profile.User
import com.example.postily.network.RetrofitInstance
import com.example.postily.network.UserApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: UserApiService,
    private val firestore: FirebaseFirestore
) {
    companion object {
        const val USERS_COLLECTION = "users"
        const val FRIENDS_COLLECTION = "friends"
    }

    // Fetch user from Firestore or fallback to API if not available
    suspend fun getUser(): User? {
        return try {
            val snapshot = firestore.collection(USERS_COLLECTION).document("1").get().await()
            val user = snapshot.toObject(User::class.java)

            if (user == null) {
                // If the user is not available in Firestore, fetch from API
                val userFromApi = fetchUserFromApi()
                // Save user to Firestore after fetching from API
                saveUserToFirestore(userFromApi)
                userFromApi
            } else {
                user
            }
        } catch (e: Exception) {
            null
        }
    }

    // Fetch friends from Firestore or fallback to API if not available
    suspend fun getFriends(): List<User> {
        return try {
            val snapshot = firestore.collection(FRIENDS_COLLECTION).get().await()
            val friends = snapshot.documents.mapNotNull { it.toObject(User::class.java) }

            if (friends.isEmpty()) {
                // If friends are not available in Firestore, fetch from API
                val friendsFromApi = fetchFriendsFromApi()
                // Save friends to Firestore after fetching from API
                saveFriendsToFirestore(friendsFromApi)
                friendsFromApi
            } else {
                friends
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Save user data to Firestore
    suspend fun saveUserToFirestore(user: User) {
        try {
            firestore.collection(USERS_COLLECTION).document(user.id.toString()).set(user).await()
        } catch (e: Exception) {
            // Handle any errors
        }
    }

    // Save friends to Firestore
    suspend fun saveFriendsToFirestore(friends: List<User>) {
        try {
            val batch = firestore.batch()
            friends.forEach { friend ->
                val document = firestore.collection(FRIENDS_COLLECTION).document(friend.id.toString())
                batch.set(document, friend)
            }
            batch.commit().await()
        } catch (e: Exception) {
            // Handle any errors when saving to Firestore
        }
    }

    // Fetch user from the API
    suspend fun fetchUserFromApi(): User {
        return apiService.getUser(1)
    }

    // Fetch friends from the API
    suspend fun fetchFriendsFromApi(): List<User> {
        return apiService.getFriends()
    }
}
