package com.example.eyetrainerapp.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import com.example.eyetrainerapp.R
import com.example.eyetrainerapp.data.entities.ExerciseEntity
import com.example.eyetrainerapp.data.sqlite.DBOpenHelper
import java.util.*


class ExerciseActivity : AppCompatActivity(){
    lateinit var name: TextView
    lateinit var instruction: TextView

    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val exerciseId: Int = intent.getIntExtra("exerciseId", 0)

        val databaseHelper = DBOpenHelper(this)

        val exercise: ExerciseEntity? = databaseHelper.getExerciseById(exerciseId)

        name = findViewById(R.id.exerciseName)
        instruction = findViewById(R.id.exerciseInstruction)


        name.text = exercise!!.name
        instruction.text = exercise!!.instruction

        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.ENGLISH)
            }
        })

    }

    fun onBackListener(view: View) {
        onPause(view)
        val goToMainIntent = Intent(this, HomeActivity::class.java)
        startActivity(goToMainIntent)
    }

    fun onPlayListener(view: View) {
        if (textToSpeech == null) {
            textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {status ->
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH)
                }
            })
        }
        textToSpeech.speak(instruction.text as String?, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun onPause(view: View) {
        textToSpeech.stop()
    }


}
