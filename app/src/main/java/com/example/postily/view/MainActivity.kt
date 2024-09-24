package com.example.postily.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.postily.R
import com.example.postily.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.postily.model.tasks.Task
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_feed,
                R.id.navigation_albums,
                R.id.navigation_tasks,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.labelVisibilityMode = LABEL_VISIBILITY_LABELED

        FirebaseApp.initializeApp(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.bottom_nav_menu, menu) // Inflate the toolbar menu
        return true
    }

//    fun saveTask(task: Task){
//        val taskMap = hashMapOf(
//            "title" to task.title
//        )
//
//        db.collection("tasks")
//            .add(taskMap)
//            .addOnSuccessListener { documentReference ->
//                Log.d("Firestore", "Task added with ID: ${documentReference.id}")
//            }
//
//            .addOnFailureListener{ e ->
//                Log.w("Firestore", "Error adding task", e)
//            }
//    }
//
//    fun shareImage(imageUri: Uri){
//        val shareIntent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_STREAM, imageUri)
//            type = "image/jpeg"
//        }
//        startActivity(Intent.createChooser(shareIntent, "Share Image"))
//    }
//
//    fun openEmailApp(email: String){
//        val intent = Intent(Intent.ACTION_SENDTO).apply {
//            data = Uri.parse("mailto:$email")
//        }
//        startActivity(intent)
//    }
//
//    fun dialPhoneNumber(phoneNumber: String){
//        val intent = Intent(Intent.ACTION_DIAL).apply {
//            data = Uri.parse("tel:$phoneNumber")
//        }
//        startActivity(intent)
//    }
//
//    fun openMap(location: String){
//        val gmmIntentUri = Uri.parse("geo:0,0?q=$location")
//        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
//        startActivity(mapIntent)
//    }
}