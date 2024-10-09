package com.example.postily.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.postily.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val viewModel: AuthViewModel by viewModels()

                viewModel.handleGoogleSignInResult(data,
                    onSuccess = { /* Handle success */ },
                    onFailure = { Log.e("Auth", "Google Sign-In Failed", it) }
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostilyTheme {
                MainScreen(onGoogleSignIn = { intent -> googleSignInLauncher.launch(intent) })
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
    val navController = rememberNavController()
    val isUserSignedIn by viewModel.isUserSignedIn.collectAsState(initial = false)

    if (isUserSignedIn) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                "Postily",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavigationGraph(navController = navController, paddingValues = innerPadding, onGoogleSignIn = onGoogleSignIn)
        }
    } else {
        AuthScreen(navController = navController, onGoogleSignIn = onGoogleSignIn)
    }
}