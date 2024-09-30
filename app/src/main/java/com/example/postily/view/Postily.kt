package com.example.postily.view

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PostilyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Any additional setup if needed
    }
}
