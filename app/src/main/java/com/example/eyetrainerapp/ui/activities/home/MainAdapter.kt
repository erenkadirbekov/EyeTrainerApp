package com.example.eyetrainerapp.ui.activities.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eyetrainerapp.R
import com.example.eyetrainerapp.data.entities.ExerciseEntity
import com.example.eyetrainerapp.utils.startExerciseActivity

class MainAdapter(var items: List<ExerciseEntity>, val context : Context) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val exerciseName = itemView.findViewById<TextView>(R.id.exerciseName)
        private val exerciseDescription = itemView.findViewById<TextView>(R.id.exerciseDescription)

        fun bind(item: ExerciseEntity) {
            exerciseName.text = item.name
            exerciseDescription.text = item.description
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) startExerciseActivity(item.id, context)

            }
        }
    }

}