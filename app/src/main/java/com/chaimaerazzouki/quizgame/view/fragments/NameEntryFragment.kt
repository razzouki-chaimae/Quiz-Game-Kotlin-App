package com.chaimaerazzouki.quizgame.view.fragments

// import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.viewmodel.NameEntryViewModel
import android.app.AlertDialog
import android.app.AlertDialog.Builder;

class NameEntryFragment : Fragment() {

    // Declare variables
    private lateinit var submitPlayerNameButton : Button
    private lateinit var playerNameEditText : EditText
    private lateinit var nameEntryViewModel: NameEntryViewModel // Declare the nameEntryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the NameEntryViewModel
        nameEntryViewModel = ViewModelProvider(requireActivity())[NameEntryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment's layout
        val rootView = inflater.inflate(R.layout.fragment_name_entry, container, false)

        // Initialize fragment widgets
        submitPlayerNameButton = rootView.findViewById(R.id.submitPlayerNameButton)
        playerNameEditText = rootView.findViewById(R.id.playerNameEditText)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitPlayerNameButton.setOnClickListener {
            // Get the entered name
            val playerName = playerNameEditText.text.toString()
            // If the player's name is not empty or null we can navigate to the theme selection fragment
            // Else, we ask the player to set a name
            if (!playerName.isNullOrEmpty()){
                // Store the player's name
                nameEntryViewModel.setPlayerName(playerName)
                // Navigate to the theme selection fragment
                findNavController().navigate(R.id.action_nameEntryFragment_to_themeSelectionFragment)
            } else {
                showWarningDialog()
            }
        }
    }

    // Define the dialog to display when the user try to start the game without entering his name
    private fun showWarningDialog() {
        val builder = Builder(requireContext())
        builder.setTitle("Player Name Required") // Set the title
        builder.setMessage("Please enter your name!") // Set the message
        // builder.setIcon(R.drawable.ic_warning) // Add an icon
        builder.setPositiveButton("OK") { dialog, _ -> // Define the action when the "OK" button is clicked
            dialog.dismiss()
        } // set OK button
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}