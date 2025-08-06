package com.example.colortapper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colortapper.databinding.FragmentStartBinding

/**
 * Start screen fragment shown when the app launches
 * Displays game title, instructions, and start button
 */
class StartFragment : Fragment() {
    
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
    }
    
    /**
     * Sets up click listeners for UI elements
     */
    private fun setupClickListeners() {
        binding.startButton.setOnClickListener {
            startGame()
        }
        
        binding.instructionsButton.setOnClickListener {
            showInstructions()
        }
    }
    
    /**
     * Navigates to the game screen
     */
    private fun startGame() {
        findNavController().navigate(
            StartFragmentDirections.actionStartFragmentToGameFragment()
        )
    }
    
    /**
     * Shows game instructions
     */
    private fun showInstructions() {
        val instructions = """
🎯 Color Tapper Game Instructions

🎮 How to Play:
• Tap only the tiles that match the target color
• Be quick! You have 30 seconds per round
• Correct taps: +10 points
• Wrong taps: -5 points
• Target color changes every 5 seconds

🏆 Scoring:
• Score as many points as possible
• Try to maintain high accuracy
• Beat your high score!

💡 Tips:
• Focus on the target color display
• Stay calm and tap quickly
• Wrong taps hurt your score, so be careful!

Good luck and have fun! 🎉
        """.trimIndent()
        
        // Create and show dialog with instructions
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Game Instructions")
            .setMessage(instructions)
            .setPositiveButton("Got it!") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
