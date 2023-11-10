package com.chaimaerazzouki.quizgame.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chaimaerazzouki.quizgame.model.QuestionEntity
import com.chaimaerazzouki.quizgame.view.fragments.QuestionFragment
import com.chaimaerazzouki.quizgame.view.listeners.OnAnswerSelectedListener

class QuestionPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val questions = mutableListOf<QuestionEntity>()

    var answerSelectedListener: OnAnswerSelectedListener? = null

    override fun getItem(position: Int): Fragment {
        val fragment = QuestionFragment.newInstance(questions[position])
        fragment.answerSelectedListener = answerSelectedListener
        return fragment
    }

    override fun getCount(): Int {
        return questions.size
    }

    fun setQuestions(questionList: List<QuestionEntity>) {
        questions.clear()
        questions.addAll(questionList)
        notifyDataSetChanged()
    }
}