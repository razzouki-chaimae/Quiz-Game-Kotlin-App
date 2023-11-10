package com.chaimaerazzouki.quizgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameEntryViewModel : ViewModel() {

    // MutableLiveData to hold the player's name
    private val _playerName = MutableLiveData<String>()

    // Expose an immutable LiveData to observe the player's name
    val playerName: LiveData<String> get() = _playerName

    // Function to update the player's name
    fun setPlayerName(name: String) {
        _playerName.value = name
    }
}