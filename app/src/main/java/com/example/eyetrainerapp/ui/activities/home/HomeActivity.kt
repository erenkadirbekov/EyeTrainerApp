package com.example.eyetrainerapp.ui.activities.home

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyetrainerapp.R
import com.example.eyetrainerapp.data.entities.ExerciseEntity
import com.example.eyetrainerapp.data.sqlite.DBOpenHelper
import com.example.eyetrainerapp.domain.HomeViewModel
import com.example.eyetrainerapp.domain.HomeViewModelFactory
import com.example.eyetrainerapp.utils.startExerciseActivity
import com.example.eyetrainerapp.utils.startHomeActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class HomeActivity : AppCompatActivity() {



    private lateinit var dbHelper: DBOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        System.out.println("I'm here 4")

        dbHelper = DBOpenHelper(this)
        var database: SQLiteDatabase = baseContext.openOrCreateDatabase("EyeTrainerAppDB.db", MODE_PRIVATE, null)
        dbHelper.onCreate(database)

        if (isTableEmpty(database, "exercises")) {
            System.out.println("I'm here 3")
            val exercise1 = ExerciseEntity(
                1,
                "Focus change",
                "This exercise works by challenging your focus. It should be done from a seated position.",
                "Hold your pointer finger a few inches away from your eye. Focus on your finger. Slowly move your finger away from your face, holding your focus. Look away for a moment, into the distance. Focus on your outstretched finger and slowly bring it back toward your eye. Look away and focus on something in the distance. Repeat three times."
            )
            val exercise2 = ExerciseEntity(
                2,
                "Near and far focus",
                "This is another focus exercise. As with the previous one, it should be done from a seated position.",
                "Hold your thumb about 10 inches from your face and focus on it for 15 seconds. Find an object roughly 10 to 20 feet away, and focus on it for 15 seconds. Return your focus to your thumb. Repeat five times."
            )
            val exercises: ArrayList<ExerciseEntity> = ArrayList()
            exercises.add(exercise1)
            exercises.add(exercise2)
            dbHelper.addExercise(exercise1)
            dbHelper.addExercise(exercise2)

        }

        var recyclerView = findViewById<RecyclerView>(com.example.eyetrainerapp.R.id.myRecycler)
        val items: List<ExerciseEntity> = dbHelper.getAllExercises()


        val adapter = MainAdapter(items, object: MainAdapter.Callback {
            override fun onItemClicked(item: ExerciseEntity) {
                print("Here " + item.id)
                startExerciseActivity(item.id)
            }
        })

        recyclerView.adapter = adapter
    }

    fun isTableEmpty(database: SQLiteDatabase, tableName: String): Boolean {

        val flag: Boolean
        val quString = "select exists(select 1 from $tableName);"

        val cursor = database.rawQuery(quString, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        if (count == 1) {
            flag = false
        } else {
            flag = true
        }
        cursor.close()
        database.close()

        return flag
    }
}
