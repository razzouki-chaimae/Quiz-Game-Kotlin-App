package com.chaimaerazzouki.quizgame.view.listeners

import com.chaimaerazzouki.quizgame.model.QuestionEntity

interface OnAnswerSelectedListener {

    fun onAnswerSelected(question: QuestionEntity, selectedAnswer: Int)
}