package com.example.postily.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.postily.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Register result for Google Sign-In
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val viewModel: AuthViewModel by viewModels()

                // Handle the result in the ViewModel
                viewModel.handleGoogleSignInResult(data,
                    onSuccess = { Log.d("AuthSuccess","Google Sign-In Succeed") },
                    onFailure = { Log.e("AuthFailure", "Google Sign-In Failed") })
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostilyTheme {
                MainScreen(onGoogleSignIn = { intent -> googleSignInLauncher.launch(intent) })
                Log.d("onCreate", "Hey")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onGoogleSignIn: (Intent) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    // Create NavController
    val navController = rememberNavController()

    // Observe the sign-in status
    val isUserSignedIn by viewModel.isUserSignedIn.collectAsState()

    // Show authentication screen if not signed in, else show the main content
    if (isUserSignedIn) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Postily") })
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavigationGraph(
                navController = navController,
                paddingValues = innerPadding,
                onGoogleSignIn = onGoogleSignIn
            )
        }
    } else {
//        AuthScreen(onGoogleSignIn = onGoogleSignIn)
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Postily") })
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavigationGraph(
                navController = navController,
                paddingValues = innerPadding,
                onGoogleSignIn = onGoogleSignIn
            )
        }
    }
}