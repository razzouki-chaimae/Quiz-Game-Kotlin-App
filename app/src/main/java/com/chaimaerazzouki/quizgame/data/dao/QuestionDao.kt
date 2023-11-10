package com.chaimaerazzouki.quizgame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chaimaerazzouki.quizgame.model.QuestionEntity

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuestionEntity>

    // Get random 20 questions of the specified theme
    @Query("SELECT * FROM questions WHERE questionTheme = :theme ORDER BY RANDOM() LIMIT 20")
    suspend fun getQuestionsByTheme(theme : String): List<QuestionEntity>
}