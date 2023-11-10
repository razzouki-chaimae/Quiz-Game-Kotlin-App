package com.chaimaerazzouki.quizgame.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.viewmodel.QuizViewModel

class ResultsFragment : Fragment() {

    private lateinit var resultIllustrationImageView : ImageView
    private lateinit var playerFinalScore : TextView
    private lateinit var resultMessageTextView : TextView
    private lateinit var playAgainButton : Button

    private lateinit var quizViewModel : QuizViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the ViewModels
        quizViewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_results, container, false)

        resultIllustrationImageView = rootView.findViewById(R.id.resultIllustrationImageView)
        playerFinalScore = rootView.findViewById(R.id.playerFinalScore)
        resultMessageTextView = rootView.findViewById(R.id.resultMessageTextView)
        playAgainButton = rootView.findViewById(R.id.playAgainButton)

        setResultFragmentContent()

        playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultsFragment_to_themeSelectionFragment)
        }

        // reset game data ( score )
        quizViewModel.reset()

        return rootView
    }

    private fun setResultFragmentContent() {
        // get the final score
        val finalScore = quizViewModel.score.value

        if (finalScore != null) {

            playerFinalScore.text = "Final Score : $finalScore/20"

            if (finalScore >= 10) {
                // The player won
                resultIllustrationImageView.setImageResource(R.drawable.winning_illustration)
                playerFinalScore.setTextColor(resources.getColor(R.color.green))
                resultMessageTextView.text = "You win!"
            } else {
                // The player loose
                resultIllustrationImageView.setImageResource(R.drawable.loosing_illustration)
                playerFinalScore.setTextColor(resources.getColor(R.color.red))
                resultMessageTextView.text = "You loose!"
            }
        }
    }

}