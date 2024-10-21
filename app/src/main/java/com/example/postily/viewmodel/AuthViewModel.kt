package com.example.postily.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val isUserSignedIn = authRepository.isUserSignedIn

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _currentUser = MutableStateFlow<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    init {
        firebaseAuth.addAuthStateListener { auth ->
            _currentUser.value = auth.currentUser
        }
    }

    fun googleSignInIntent(context: Context): Intent {
        return authRepository.getGoogleSignInIntent(context)
    }

    fun handleGoogleSignInResult(
        data: Intent?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    authRepository.signInWithGoogle(idToken).fold(
                        onSuccess = {
                            _authState.value = AuthState.Success
                            onSuccess()
                        },
                        onFailure = { e ->
                            _authState.value = AuthState.Failure(e.message ?: "Unknown error")
                            onFailure(e as Exception)
                        }
                    )
                }
            } catch (e: ApiException) {
                _authState.value = AuthState.Failure("Google Sign-In Failed: ${e.statusCode}")
                onFailure(e)
            }
        }
    }

    fun registerWithEmailAndPassword(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _authState.value = AuthState.Failure("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                authRepository.registerWithEmailAndPassword(email, password).fold(
                    onSuccess = {
                        _authState.value = AuthState.Success
                    },
                    onFailure = { e ->
                        _authState.value = AuthState.Failure(e.message ?: "Registration failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Failure("Registration error: ${e.message}")
            }
        }
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                authRepository.loginWithEmailAndPassword(email, password).fold(
                    onSuccess = {
                        _authState.value = AuthState.Success
                    },
                    onFailure = { e ->
                        _authState.value = AuthState.Failure(e.message ?: "Login failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Failure("Login error: ${e.message}")
            }
        }
    }

    fun logout() {
        authRepository.signOut()
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Failure(val message: String) : AuthState()
}
