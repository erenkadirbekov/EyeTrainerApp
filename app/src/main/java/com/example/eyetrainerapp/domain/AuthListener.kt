package com.example.eyetrainerapp.domain

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}