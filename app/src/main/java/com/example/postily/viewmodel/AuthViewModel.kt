package com.example.postily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String): LiveData<Boolean>{
        val result = MutableLiveData<Boolean>()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            result.value = task.isSuccessful
        }
        return result
    }
}