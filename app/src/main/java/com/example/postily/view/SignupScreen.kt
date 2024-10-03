package com.example.postily.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.postily.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    onGoogleSignIn: (Intent) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to Postily", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = "e.g. john.doe@jaguarlandrover.com",
                onValueChange = {},
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            TextField(
                value = "your password",
                onValueChange = {},
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    Toast.makeText(context, "Sign in clicked!", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Sign in with Google")
            }
//            Button(
//                onClick = {
//                    val signInIntent = authViewModel.googleSignInIntent(context)
//                    onGoogleSignIn(signInIntent)
//                }
//            ) {
//                Text("Sign in with Google")
//            }
        }
    }
}