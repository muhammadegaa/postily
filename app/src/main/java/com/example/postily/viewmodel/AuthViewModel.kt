package com.example.postily.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.R
import com.example.postily.model.profile.User
import com.example.postily.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isUserSignedIn = authRepository.isUserSignedIn

    fun googleSignInIntent(context: Context): Intent {
        return authRepository.getGoogleSignInIntent(context)

    }

    fun handleGoogleSignInResult(
        data: Intent?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        Log.d("AuthViewModel", "Yes you are here")
        viewModelScope.launch {
            Log.d("viewModelScope", "Hello")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    Log.d("AuthViewModel", "Google ID Token received: $idToken")
                    authRepository.signInWithGoogle(idToken).fold(
                        onSuccess = {
                            Log.d("AuthViewModel", "Google Sign-In Success")
                            onSuccess()
                        },
                        onFailure = { e ->
                            Log.e("AuthViewModel", "Google Sign-In Failed: ${e.message}")
                            onFailure(e as Exception)
                        }
                    )
                }
            } catch (e: ApiException) {
                Log.e("AuthViewModel", "Google Sign-In Failed with ApiException: ${e.statusCode}")
                onFailure(e)
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Failure(val message: String) : AuthState()
}