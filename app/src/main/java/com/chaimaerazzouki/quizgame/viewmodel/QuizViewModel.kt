package com.chaimaerazzouki.quizgame.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaimaerazzouki.quizgame.data.repositories.QuestionRepository
import com.chaimaerazzouki.quizgame.model.QuestionEntity
import kotlinx.coroutines.launch

class QuizViewModel(private val application : Application) : ViewModel() {

    // MutableLiveData to hold the questions relative to the selected theme
    private val _themeQuestions = MutableLiveData<List<QuestionEntity>>()
    // MutableLiveData to hold the score of the player
    private val _score = MutableLiveData(0)


    // Expose an immutable LiveData to observe the questions relative to the selected theme
    val themeQuestions: LiveData<List<QuestionEntity>> get() = _themeQuestions
    // Expose an immutable LiveData to observe the score of the player
    val score: LiveData<Int> get() = _score


    // Function to update the questions relative to the selected theme
    private fun setThemeQuestions(themeQuestions : List<QuestionEntity>) {
        _themeQuestions.value = themeQuestions
    }
    // Function to update the score of the player
    fun setScore(score : Int) {
        _score.value = score
    }


    fun retrieveQuestionByTheme(theme : String){
        val context = application.applicationContext
        val repository = QuestionRepository(context)

        viewModelScope.launch {
            setThemeQuestions(repository.getQuestionsByTheme(theme))
        }
    }

    // reset the data relative to the finished round
    fun reset() {
        setThemeQuestions(emptyList())
        setScore(0)
    }
}