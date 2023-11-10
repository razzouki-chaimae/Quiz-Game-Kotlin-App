package com.chaimaerazzouki.quizgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeSelectionViewModel : ViewModel() {

    // MutableLiveData to hold the selectedTheme
    private val _selectedTheme = MutableLiveData<String>()

    // Expose an immutable LiveData to observe the selectedTheme
    val selectedTheme: LiveData<String> get() = _selectedTheme

    // Function to update the selectedTheme
    fun setSelectedTheme(themeName : String) {
        _selectedTheme.value = themeName
    }
}