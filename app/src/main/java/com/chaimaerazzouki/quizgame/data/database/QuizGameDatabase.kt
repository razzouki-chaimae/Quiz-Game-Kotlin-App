package com.chaimaerazzouki.quizgame.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chaimaerazzouki.quizgame.data.dao.QuestionDao
import com.chaimaerazzouki.quizgame.data.nonprimitivetypesconverters.StringListConverter
import com.chaimaerazzouki.quizgame.data.repositories.QuestionRepository
import com.chaimaerazzouki.quizgame.model.QuestionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [QuestionEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class QuizGameDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: QuizGameDatabase? = null

        fun getDatabase(context: Context): QuizGameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizGameDatabase::class.java,
                    "quiz_game_database"
                ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // Get the questions to add to the database
                            val json = context.assets.open("GameQuestions.json").bufferedReader().use {
                                it.readText()
                            }
                            val questions: List<QuestionEntity> = Gson().fromJson(json, object : TypeToken<List<QuestionEntity>>() {}.type)

                            // Insert initial data through the repository
                            val repository = QuestionRepository(context)
                            CoroutineScope(Dispatchers.IO).launch {
                                repository.insertInitialQuestions(questions)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}