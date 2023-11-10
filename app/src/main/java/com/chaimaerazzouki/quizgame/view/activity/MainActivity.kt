package com.chaimaerazzouki.quizgame.view.activity

//import android.R
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.viewmodel.NameEntryViewModel
import com.chaimaerazzouki.quizgame.viewmodel.QuizViewModel
import com.chaimaerazzouki.quizgame.viewmodel.ThemeSelectionViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    // Global initialisations
    // Declare the NameEntryViewModel so the player's name can be visible to all the fragments
    val nameEntryViewModel by viewModels<NameEntryViewModel>()
    val themeSelectionViewModel by viewModels<ThemeSelectionViewModel>()
    val quizViewModel by viewModels<QuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        // Allow getting notifications
        FirebaseMessaging.getInstance().isAutoInitEnabled = true


        // val navController = findNavController(this, R.id.nav_host_fragment)
        // setupActionBarWithNavController(this, navController)

    }
}