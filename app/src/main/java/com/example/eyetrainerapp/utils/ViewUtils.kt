package com.example.eyetrainerapp.utils

import android.content.Context
import android.content.Intent
import com.example.eyetrainerapp.ui.activities.auth.LoginActivity
import com.example.eyetrainerapp.ui.activities.home.ExerciseActivity
import com.example.eyetrainerapp.ui.activities.home.HomeActivity

fun Context.startHomeActivity() =
    Intent(this, HomeActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }


fun Context.startExerciseActivity(id: Int) =
    Intent(this, ExerciseActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        it.putExtra("exerciseId", id)
        startActivity(it)
    }