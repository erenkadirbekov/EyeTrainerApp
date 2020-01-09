package com.example.eyetrainerapp.data.sqlite


import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.eyetrainerapp.data.entities.ExerciseEntity
import java.io.File
import java.io.FileOutputStream
import java.lang.RuntimeException

class DBOpenHelper(val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "eyeTrainerAppDB"
        private val TABLE_EXERCISES = "exercises"
        private const val ASSETS_PATH = "databases"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    private fun installedDatabaseIsOutdated() : Boolean {
        return sharedPreferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        sharedPreferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

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

    fun installDatabaseFromAssets() {
        val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME.db")
        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()
        }
        catch (exception: Throwable) {
            throw RuntimeException("$DATABASE_NAME cannot be installed", exception)
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }
}