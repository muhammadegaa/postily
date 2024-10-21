package com.example.postily.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postily.R
import com.example.postily.viewmodel.AuthState
import com.example.postily.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    navController: NavController,
    onGoogleSignIn: (Intent) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var isLogin by remember { mutableStateOf(true) } // Track if it's login or signup screen
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") } // Only needed for Sign Up

    val context = LocalContext.current

    // Show error message from ViewModel state
    val authState by authViewModel.authState.collectAsState()

    // Email validation function
    fun validateEmail(): Boolean {
        return if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    // Password validation function
    fun validatePassword(): Boolean {
        return if (password.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    // Confirm Password validation function (for Sign-Up)
    fun validateConfirmPassword(): Boolean {
        return if (confirmPassword != password) {
            Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    // Handle possible AuthState error
    LaunchedEffect(authState) {
        if (authState is AuthState.Failure) {
            val errorMessage = (authState as AuthState.Failure).message
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/300/200")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.sample_image),
                contentDescription = stringResource(R.string.auth_header_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (isLogin) "Welcome Back" else "Create an Account",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Input fields
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.8f),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.8f),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Confirm Password only for Sign-Up
            if (!isLogin) {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Action Button
            Button(
                onClick = {
                    val isEmailValid = validateEmail()
                    val isPasswordValid = validatePassword()
                    val isConfirmPasswordValid = if (!isLogin) validateConfirmPassword() else true

                    if (isEmailValid && isPasswordValid && isConfirmPasswordValid) {
                        if (isLogin) {
                            // Call login function from ViewModel
                            authViewModel.loginWithEmailAndPassword(email, password)
                        } else {
                            // Call sign-up function from ViewModel
                            authViewModel.registerWithEmailAndPassword(email, password, confirmPassword)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(if (isLogin) "Sign In" else "Sign Up")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Toggle between Login and Signup
            TextButton(
                onClick = {
                    isLogin = !isLogin // Toggle between login and signup
                }
            ) {
                Text(
                    if (isLogin) "Don't have an account? Sign Up" else "Already have an account? Sign In",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}