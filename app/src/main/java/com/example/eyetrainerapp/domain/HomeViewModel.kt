package com.example.eyetrainerapp.domain

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.eyetrainerapp.data.repositories.UserRepository
import com.example.eyetrainerapp.utils.startLoginActivity

class HomeViewModel (private val repository: UserRepository) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View) {
        repository.logout()
        view.context.startLoginActivity()
    }
}