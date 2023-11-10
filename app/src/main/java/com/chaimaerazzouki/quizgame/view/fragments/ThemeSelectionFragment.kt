package com.chaimaerazzouki.quizgame.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.model.ThemesItem
import com.chaimaerazzouki.quizgame.view.adapters.ThemesAdapter
import com.chaimaerazzouki.quizgame.view.listeners.OnItemClickListener
import com.chaimaerazzouki.quizgame.viewmodel.NameEntryViewModel
import com.chaimaerazzouki.quizgame.viewmodel.QuizViewModel
import com.chaimaerazzouki.quizgame.viewmodel.ThemeSelectionViewModel
import com.chaimaerazzouki.quizgame.viewmodel.viewmodelfactory.QuizViewModelFactory

// I implemented the OnItemClickListener to handle selected theme
class ThemeSelectionFragment : Fragment(), OnItemClickListener {

    // Declare variables
    private lateinit var nameEntryViewModel: NameEntryViewModel // Declare the nameEntryViewModel
    private lateinit var themeSelectionViewModel : ThemeSelectionViewModel
    private lateinit var quizViewModel : QuizViewModel
    private lateinit var playerNameTextView : TextView
    private lateinit var themesRecyclerView: RecyclerView
    private lateinit var themeQuestionsProgressBar : ProgressBar
    // the list of themes
    // TODO : Retrieve it from database
    val themesItems = listOf(
        ThemesItem("History",  R.drawable.history_illustration),
        ThemesItem("Geography", R.drawable.geography_illustration),
        ThemesItem("Sport", R.drawable.sports_illustration)
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the ViewModels
        nameEntryViewModel = ViewModelProvider(requireActivity())[NameEntryViewModel::class.java]
        themeSelectionViewModel = ViewModelProvider(requireActivity())[ThemeSelectionViewModel::class.java]
        quizViewModel = ViewModelProvider(requireActivity(), QuizViewModelFactory(requireActivity().application))[QuizViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment's layout
        val rootView = inflater.inflate(R.layout.fragment_theme_selection, container, false)

        playerNameTextView = rootView.findViewById(R.id.greetingPlayerTextView)

        themesRecyclerView = rootView.findViewById(R.id.themesRecyclerView)
        themesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        themesRecyclerView.adapter = ThemesAdapter(themesItems, this)

        themeQuestionsProgressBar = rootView.findViewById(R.id.themeQuestionsProgressBar)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerNameTextView.text = "Hello "+nameEntryViewModel.playerName.value+"!"
    }

    override fun onItemClick(theme: String) {
        // Disable UI interaction
        themesRecyclerView.isEnabled = false

        // show the progress bar
        themeQuestionsProgressBar.visibility = View.VISIBLE

        // we store the the selected theme so we can work with it in quiz fragment
        themeSelectionViewModel.setSelectedTheme(theme)

        // Retrieve questions based on the selected theme
        //themeSelectionViewModel.selectedTheme.value?.let { it1 ->
        quizViewModel.retrieveQuestionByTheme(theme)
        //}

        // Observe the LiveData for the retrieved data
        quizViewModel.themeQuestions.observe(viewLifecycleOwner) { questions ->
            // Hide the progress bar
            themeQuestionsProgressBar.visibility = View.GONE

            // Enable UI interaction
            themesRecyclerView.isEnabled = true

            // Check if data retrieval was successful
            if (questions.isNotEmpty()) {
                // Navigate to the quiz fragment
                findNavController().navigate(R.id.action_themeSelectionFragment_to_quizFragment)
            } else {
                // Handle the case where no data was retrieved  by showing an error message
                showErrorDialog()
            }
        }
    }

    // Define the dialog to display if the user try to start the game without entering his name
    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Data retrieval Error") // Set the title
        builder.setMessage("We are sorry, we couldn't retrieve the questions relative to the selected theme. Try again!") // Set the message
        // builder.setIcon(R.drawable.ic_warning) // Add an icon
        builder.setPositiveButton("OK") { dialog, _ -> // Define the action when the "OK" button is clicked
            dialog.dismiss()
        } // set OK button
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}