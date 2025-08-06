package com.example.colortapper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.colortapper.databinding.FragmentGameOverBinding
import com.example.colortapper.viewmodel.GameViewModel

/**
 * Game over fragment shown when the time runs out
 * Displays final score, statistics, and restart option
 */
class GameOverFragment : Fragment() {
    
    private var _binding: FragmentGameOverBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: GameViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
        displayGameOverStats()
    }
    
    /**
     * Sets up click listeners for UI elements
     */
    private fun setupClickListeners() {
        binding.restartButton.setOnClickListener {
            restartGame()
        }
        
        binding.backToMenuButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    /**
     * Displays game over statistics and final score
     */
    private fun displayGameOverStats() {
        // Get arguments passed from GameFragment
        val args = arguments?.let {
            GameOverFragmentArgs.fromBundle(it)
        }
        
        val finalScore = args?.finalScore ?: 0
        val correctTaps = args?.correctTaps ?: 0
        val wrongTaps = args?.wrongTaps ?: 0
        val roundsPlayed = args?.roundsPlayed ?: 1
        
        // Calculate accuracy
        val totalTaps = correctTaps + wrongTaps
        val accuracy = if (totalTaps > 0) {
            (correctTaps * 100 / totalTaps)
        } else {
            0
        }
        
        // Display statistics
        binding.finalScoreText.text = "Final Score: $finalScore"
        binding.correctTapsText.text = "Correct Taps: $correctTaps"
        binding.wrongTapsText.text = "Wrong Taps: $wrongTaps"
        binding.accuracyText.text = "Accuracy: ${accuracy}%"
        binding.roundsPlayedText.text = "Rounds Played: $roundsPlayed"
        
        // Set performance message based on score
        val performanceMessage = when {
            finalScore >= 200 -> "🏆 Outstanding! You're a Color Tapping Master!"
            finalScore >= 150 -> "🌟 Excellent! Great reflexes!"
            finalScore >= 100 -> "👍 Good job! Keep practicing!"
            finalScore >= 50 -> "😊 Not bad! Try to improve your accuracy!"
            else -> "💪 Keep practicing! You'll get better!"
        }
        
        binding.performanceText.text = performanceMessage
    }
    
    /**
     * Restarts the game by navigating back to GameFragment
     */
    private fun restartGame() {
        findNavController().navigate(
            GameOverFragmentDirections.actionGameOverFragmentToGameFragment()
        )
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
