package com.example.postily.repository

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) {
    private val _isUserSignedIn = MutableStateFlow(firebaseAuth.currentUser != null)
    val isUserSignedIn: StateFlow<Boolean> get() = _isUserSignedIn

    suspend fun signInWithGoogle(idToken: String): Result<AuthResult> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            _isUserSignedIn.value = true
            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _isUserSignedIn.value = false
    }

    fun getGoogleSignInIntent(context: Context): Intent {
        return googleSignInClient.signInIntent
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}