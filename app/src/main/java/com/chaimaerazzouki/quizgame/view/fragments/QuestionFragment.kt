package com.chaimaerazzouki.quizgame.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.model.QuestionEntity
import com.chaimaerazzouki.quizgame.view.listeners.OnAnswerSelectedListener
import com.chaimaerazzouki.quizgame.viewmodel.QuizViewModel

class QuestionFragment : Fragment() {
    private lateinit var currentQuestionEntity: QuestionEntity

    private lateinit var countdownTextView :TextView
    private lateinit var currentQuestionTextView : TextView
    private lateinit var option1button : Button
    private lateinit var option2button : Button
    private lateinit var option3button : Button
    private lateinit var option4button : Button

    var answerSelectedListener: OnAnswerSelectedListener? = null

    private var countdownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_question, container, false)

        countdownTextView = rootView.findViewById(R.id.countDownTextView)
        currentQuestionTextView = rootView.findViewById(R.id.currentQuestionTextView)
        option1button = rootView.findViewById(R.id.option1button)
        option2button = rootView.findViewById(R.id.option2button)
        option3button = rootView.findViewById(R.id.option3button)
        option4button = rootView.findViewById(R.id.option4button)

        currentQuestionTextView.text = currentQuestionEntity.question
        option1button.text = currentQuestionEntity.options[0]
        option2button.text = currentQuestionEntity.options[1]
        option3button.text = currentQuestionEntity.options[2]
        option4button.text = currentQuestionEntity.options[3]

        // Start the countdown timer when the question is displayed
        // startCountdownTimer(60)

        option1button.setOnClickListener{
            // set the background color of all the option buttons to the initial value
            resetBackgroundOptionsButtons()
            // set the background color of selected button to dark blue
            option1button.backgroundTintList = resources.getColorStateList(R.color.dark_blue)

            notifyAnswerSelected(currentQuestionEntity, 0)
        }
        option2button.setOnClickListener{
            // set the background color of all the option buttons to the initial value
            resetBackgroundOptionsButtons()
            // set the background color of selected button to dark blue
            option2button.backgroundTintList = resources.getColorStateList(R.color.dark_blue)

            notifyAnswerSelected(currentQuestionEntity, 1)
        }
        option3button.setOnClickListener{
            // set the background color of all the option buttons to the initial value
            resetBackgroundOptionsButtons()
            // set the background color of selected button to dark blue
            option3button.backgroundTintList = resources.getColorStateList(R.color.dark_blue)

            notifyAnswerSelected(currentQuestionEntity, 2)
        }
        option4button.setOnClickListener{
            // set the background color of all the option buttons to the initial value
            resetBackgroundOptionsButtons()
            // set the background color of selected button to dark blue
            option4button.backgroundTintList = resources.getColorStateList(R.color.dark_blue)

            notifyAnswerSelected(currentQuestionEntity, 3)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Start the countdown timer when the question is displayed
        // startCountdownTimer(60)?.start()
    }

    companion object {
        fun newInstance(question: QuestionEntity) = QuestionFragment().apply {
            this.currentQuestionEntity = question
        }
    }

    // Call this method when an answer is selected
    private fun notifyAnswerSelected(question: QuestionEntity, selectedAnswer: Int) {
        answerSelectedListener?.onAnswerSelected(question, selectedAnswer)
    }

    private fun resetBackgroundOptionsButtons() {
        option1button.backgroundTintList = resources.getColorStateList(R.color.light_blue)
        option2button.backgroundTintList = resources.getColorStateList(R.color.light_blue)
        option3button.backgroundTintList = resources.getColorStateList(R.color.light_blue)
        option4button.backgroundTintList = resources.getColorStateList(R.color.light_blue)
    }

    // Function to start the countdown timer
    private fun startCountdownTimer(totalTimeInSeconds: Long): CountDownTimer? {
        countdownTimer = object : CountDownTimer(totalTimeInSeconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the timer on the UI by displaying the remaining time
                val secondsRemaining = millisUntilFinished / 1000
                // Update your UI elements to show the remaining time
                countdownTextView.text = "seconds remaining: $secondsRemaining"
            }

            override fun onFinish() {
                // Time's up! Move to the next question
                countdownTextView.text = "Time out!"
                moveNextQuestion()
            }
        }

        return countdownTimer
    }

    // Function to move to the next question
    private fun moveNextQuestion() {
        answerSelectedListener?.onAnswerSelected(currentQuestionEntity, -1) // Notify with no answer
    }
}