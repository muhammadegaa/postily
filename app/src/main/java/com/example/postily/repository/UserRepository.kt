package com.example.postily.repository

import com.example.postily.model.profile.User
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
    suspend fun getUser(userId: Int): User? {
        return try {
            val snapshot = firestore.collection(USERS_COLLECTION).document(userId.toString()).get().await()
            val user = snapshot.toObject(User::class.java)
            user ?: fetchUserFromApi(userId).also { saveUserToFirestore(it) }
        } catch (e: Exception) {
            fetchUserFromApi(userId).also { saveUserToFirestore(it) }
        }
    }

    // Fetch friends from Firestore or fallback to API if not available
    suspend fun getFriends(): List<User> {
        return try {
            val snapshot = firestore.collection(FRIENDS_COLLECTION).get().await()
            val friends = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
            if (friends.isEmpty()) fetchFriendsFromApi().also { saveFriendsToFirestore(it) }
            else friends
        } catch (e: Exception) {
            fetchFriendsFromApi().also { saveFriendsToFirestore(it) }
        }
    }

    // Save user to Firestore
    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection(USERS_COLLECTION).document(user.id.toString()).set(user).await()
    }

    // Save friends to Firestore
    private suspend fun saveFriendsToFirestore(friends: List<User>) {
        val batch = firestore.batch()
        friends.forEach { friend ->
            val docRef = firestore.collection(FRIENDS_COLLECTION).document(friend.id.toString())
            batch.set(docRef, friend)
        }
        batch.commit().await()
    }

    // Fetch user from the API
    private suspend fun fetchUserFromApi(userId: Int): User = apiService.getUser(userId)

    // Fetch friends from the API
    private suspend fun fetchFriendsFromApi(): List<User> = apiService.getFriends()
}