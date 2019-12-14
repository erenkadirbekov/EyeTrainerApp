package com.example.eyetrainerapp.ui.activities.home

import android.annotation.SuppressLint
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
import com.example.eyetrainerapp.databinding.ActivityHomeBinding
import com.example.eyetrainerapp.domain.HomeViewModel
import com.example.eyetrainerapp.domain.HomeViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()

    private lateinit var viewModel: HomeViewModel

    private lateinit var dbHelper: DBOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel

        dbHelper = DBOpenHelper(this)
        val database: SQLiteDatabase = baseContext.openOrCreateDatabase("EyeTrainerAppDB.db", MODE_PRIVATE, null)
        dbHelper.onCreate(database)

        val exercise1: ExerciseEntity = ExerciseEntity(1, "БОЛЬШИЕ ГЛАЗА", "Укрепляет мышцы век, улучшает кровообращение, способствует расслаблению мышц глаз.", "Сидим прямо. Крепко зажмуриваем глаза на 5 секунд, затем широко открываем их. Повторяем 8-10 раз.")
        val exercise2: ExerciseEntity = ExerciseEntity(2, "СТРЕЛЯЕМ ГЛАЗАМИ", "Помогает :)", "Чертим круг по часовой стрелке и обратно, Рисуем глазами диагонали.")

        dbHelper.addExercise(exercise1)
        dbHelper.addExercise(exercise2)

        var recyclerView = findViewById<RecyclerView>(R.id.myRecycler)
        val items = dbHelper.getAllExercises()

        val adapter = MainAdapter(items, object: MainAdapter.Callback {
            override fun onItemClicked(item: ExerciseEntity) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        recyclerView.adapter = adapter
    }
}
