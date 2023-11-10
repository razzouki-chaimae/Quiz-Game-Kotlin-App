package com.chaimaerazzouki.quizgame.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val question : String,
    val options : List<String>,
    val correctAnswer : Int,
    val questionTheme : String
)
