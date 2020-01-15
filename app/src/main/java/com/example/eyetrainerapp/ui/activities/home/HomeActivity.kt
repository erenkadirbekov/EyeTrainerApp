package com.example.eyetrainerapp.ui.activities.home


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.eyetrainerapp.R
import com.example.eyetrainerapp.data.entities.ExerciseEntity
import com.example.eyetrainerapp.data.sqlite.DBOpenHelper
import com.example.eyetrainerapp.utils.startExerciseActivity

class HomeActivity : AppCompatActivity() {

    private val DATABASE_NAME = "EyeTrainerAppDB"
    private lateinit var dbHelper: DBOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dbHelper = DBOpenHelper(this)



        var recyclerView = findViewById<RecyclerView>(R.id.myRecycler)
        val items: List<ExerciseEntity> = dbHelper.getAllExercises()


        val adapter = MainAdapter(items, this)

        recyclerView.adapter = adapter
    }
}
