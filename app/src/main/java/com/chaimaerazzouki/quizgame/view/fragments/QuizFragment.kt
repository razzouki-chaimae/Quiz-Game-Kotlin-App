package com.chaimaerazzouki.quizgame.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.model.QuestionEntity
import com.chaimaerazzouki.quizgame.view.adapters.QuestionPagerAdapter
import com.chaimaerazzouki.quizgame.view.listeners.OnAnswerSelectedListener
import com.chaimaerazzouki.quizgame.viewmodel.QuizViewModel
import com.chaimaerazzouki.quizgame.viewmodel.ThemeSelectionViewModel

class QuizFragment : Fragment(), OnAnswerSelectedListener {

    private lateinit var selectedThemeNameTextView : TextView
    private lateinit var currentScoreTextView : TextView
    private lateinit var questionViewPager: ViewPager
    private lateinit var nextQuestionButton : Button

    private lateinit var pagerAdapter: QuestionPagerAdapter
    private lateinit var questionList: List<QuestionEntity>

    private lateinit var themeSelectionViewModel : ThemeSelectionViewModel
    private lateinit var quizViewModel : QuizViewModel

    private lateinit var currentQuestion : QuestionEntity
    private var playerSelectedAnswer = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the ViewModels
        themeSelectionViewModel = ViewModelProvider(requireActivity())[ThemeSelectionViewModel::class.java]
        quizViewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment's layout
        val rootView = inflater.inflate(R.layout.fragment_quiz, container, false)

        selectedThemeNameTextView = rootView.findViewById(R.id.selectedThemeNameTextView)
        currentScoreTextView = rootView.findViewById(R.id.currentScoreTextView)
        questionViewPager = rootView.findViewById(R.id.questionViewPager)
        nextQuestionButton = rootView.findViewById(R.id.nextQuestionButton)

        // Disable the next question button until the user choose an answer
        nextQuestionButton.isEnabled = false

        nextQuestionButton.setOnClickListener {

            // update the score of the player
            if (playerSelectedAnswer == currentQuestion.correctAnswer){
                quizViewModel.setScore(quizViewModel.score.value?.plus(1) ?: 1)

                showCorrectAnswerDialog("Correct Answer")
            } else {
                showCorrectAnswerDialog("Incorrect Answer")
            }
        }

        // Load your list of questions into questionList
        questionList = quizViewModel.themeQuestions.value!!

        pagerAdapter = QuestionPagerAdapter(childFragmentManager)

        // Iterate through the questionList and create and add QuestionFragments
        questionList.forEach { question ->
            val questionFragment = QuestionFragment.newInstance(question)

            // Set the answerSelectedListener for the QuestionFragment
            questionFragment.answerSelectedListener = this
        }

        questionViewPager.adapter = pagerAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedThemeNameTextView.text = themeSelectionViewModel.selectedTheme.value

        // Set up the pager adapter with the theme's questions
        pagerAdapter.setQuestions(questionList)
        pagerAdapter.answerSelectedListener = this

    }

    override fun onAnswerSelected(question: QuestionEntity, selectedAnswer: Int) {
        nextQuestionButton.isEnabled = true

        // Store the current question
        currentQuestion = question
        // Store the selected answer
        playerSelectedAnswer = selectedAnswer
    }

    // Define the dialog to display when the user try to start the game without entering his name
    private fun showCorrectAnswerDialog(message : String) {
        val builder = AlertDialog.Builder(requireContext())
        //builder.setTitle(title) // Set the title
        builder.setMessage(message) // Set the message
        // builder.setIcon(R.drawable.ic_warning) // Add an icon
        builder.setPositiveButton("OK") { dialog, _ -> // Define the action when the "OK" button is clicked
            dialog.dismiss()

            // display the next question or the result interface
            val currentItem = questionViewPager.currentItem

            if (currentItem < questionList.size - 1) {
                questionViewPager.currentItem = currentItem + 1
                // Update the displayed current score
                currentScoreTextView.text = "Score : ${quizViewModel.score.value.toString()}"
                nextQuestionButton.isEnabled = false // Disable next again for the next question
            } else {
                findNavController().navigate(R.id.action_quizFragment_to_resultsFragment)
            }

        } // set OK button
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Define the dialog to display when the user finished the game
    private fun showScoreDialog(score : Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Score") // Set the title
        builder.setMessage("You get $score/20") // Set the message
        // builder.setIcon(R.drawable.ic_warning) // Add an icon
        builder.setPositiveButton("OK") { dialog, _ -> // Define the action when the "OK" button is clicked
            dialog.dismiss()
        } // set OK button
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}