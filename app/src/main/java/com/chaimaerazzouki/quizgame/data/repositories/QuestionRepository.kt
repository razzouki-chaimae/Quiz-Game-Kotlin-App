package com.chaimaerazzouki.quizgame.data.repositories

import android.content.Context
import com.chaimaerazzouki.quizgame.data.dao.QuestionDao
import com.chaimaerazzouki.quizgame.data.database.QuizGameDatabase
import com.chaimaerazzouki.quizgame.model.QuestionEntity

class QuestionRepository(context : Context) {
    private val questionDao: QuestionDao

    init {
        val database = QuizGameDatabase.getDatabase(context)
        questionDao = database.questionDao()
    }

    suspend fun insertInitialQuestions(questions: List<QuestionEntity>) {
        // Insert the list of initial questions into the database
        questionDao.insertQuestions(questions)
    }

    suspend fun getAllQuestions(): List<QuestionEntity> {
        return questionDao.getAllQuestions()
    }

    suspend fun getQuestionsByTheme(theme : String): List<QuestionEntity> {
        return questionDao.getQuestionsByTheme(theme)
    }
}