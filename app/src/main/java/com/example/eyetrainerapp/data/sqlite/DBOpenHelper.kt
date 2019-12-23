package com.example.eyetrainerapp.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.eyetrainerapp.data.entities.ExerciseEntity

class DBOpenHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "EyeTrainerAppDB"
        private val TABLE_EXERCISES = "exercises"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_DESCRIPTION = "description"
        private val KEY_INSTRUCTION = "instruction"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val CREATE_EXERCISES_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_INSTRUCTION + " TEXT"+ ")")
        db?.execSQL(CREATE_EXERCISES_TABLE)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES)
        onCreate(db)
    }

    fun addExercise(exercise: ExerciseEntity):Long{
        val database = this.writableDatabase
        var contentValues = ContentValues()
            contentValues.put(KEY_ID, exercise.id)
            contentValues.put(KEY_NAME, exercise.name)
            contentValues.put(KEY_DESCRIPTION, exercise.description)
            contentValues.put(KEY_INSTRUCTION, exercise.instruction)
            // Inserting Row
            val success = database.insert(TABLE_EXERCISES, null, contentValues)

            contentValues.clear()
            //2nd argument is String containing nullColumnHack

        System.out.println("I'm " + success)
        database.close() // Closing database connection
        return success
    }

    fun getAllExercises():List<ExerciseEntity>{
        System.out.println("I'm 6")
        val exercisesList:ArrayList<ExerciseEntity> = ArrayList<ExerciseEntity>()
        val selectQuery = "SELECT  * FROM $TABLE_EXERCISES"
        val db = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            println("I'm " + e)
            return ArrayList()
        }
        var exerciseId: Int
        var exerciseName: String
        var exerciseDescription: String
        var exerciseInstruction: String
        if (cursor.moveToFirst()) {
            do {
                exerciseId = cursor.getInt(cursor.getColumnIndex("id"))
                exerciseName = cursor.getString(cursor.getColumnIndex("name"))
                exerciseDescription = cursor.getString(cursor.getColumnIndex("description"))
                exerciseInstruction = cursor.getString(cursor.getColumnIndex("instruction"))
                val exercise = ExerciseEntity(id = exerciseId, name = exerciseName, description = exerciseDescription, instruction = exerciseInstruction)
                exercisesList.add(exercise)
            } while (cursor.moveToNext())
        }
        return exercisesList
    }

    fun getExerciseById(id: Int): ExerciseEntity? {
        var exercise: ExerciseEntity? = null
        val selectQuery = "SELECT  * FROM $TABLE_EXERCISES WHERE id = $id"
        val db = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return null
        }
        var exerciseId: Int
        var exerciseName: String
        var exerciseDescription: String
        var exerciseInstruction: String
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex("id")) == id) {
                    exerciseId = cursor.getInt(cursor.getColumnIndex("id"))
                    exerciseName = cursor.getString(cursor.getColumnIndex("name"))
                    exerciseDescription = cursor.getString(cursor.getColumnIndex("description"))
                    exerciseInstruction = cursor.getString(cursor.getColumnIndex("instruction"))
                    exercise = ExerciseEntity(
                        id = exerciseId,
                        name = exerciseName,
                        description = exerciseDescription,
                        instruction = exerciseInstruction
                    )
                    break
                }
            } while (cursor.moveToNext())
        }
        return exercise
    }
}