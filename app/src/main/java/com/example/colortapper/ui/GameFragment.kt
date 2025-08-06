package com.example.colortapper.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.colortapper.R
import com.example.colortapper.databinding.FragmentGameBinding
import com.example.colortapper.viewmodel.GameViewModel
import com.example.colortapper.data.GameConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Main game fragment where the Color Tapper game is played
 * Displays the game grid, target color, score, and timer
 */
class GameFragment : Fragment() {
    
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: GameViewModel by viewModels()
    
    // Grid of colored tiles
    private val tiles = mutableListOf<TextView>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupGameGrid()
        setupObservers()
        setupClickListeners()
        
        // Start the game when fragment is created
        viewModel.startGame()
    }
    
    /**
     * Sets up the game grid with colored tiles
     */
    private fun setupGameGrid() {
        val gridLayout = binding.gameGrid
        gridLayout.columnCount = GameConstants.GRID_SIZE
        
        // Clear existing tiles
        tiles.clear()
        gridLayout.removeAllViews()
        
        // Create tiles for the grid
        for (i in 0 until GameConstants.GRID_SIZE * GameConstants.GRID_SIZE) {
            val tile = TextView(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(4, 4, 4, 4)
            }
            textSize = 24f
            gravity = android.view.Gravity.CENTER
            text = "●"
            setTextColor(android.graphics.Color.WHITE)
            tag = i // Store tile index for identification
        }
        
        tiles.add(tile)
        gridLayout.addView(tile)
    }
    
    /**
     * Sets up observers for LiveData from ViewModel
     */
    private fun setupObservers() {
        viewModel.gameState.observe(viewLifecycleOwner) { state ->
            updateUI(state)
        }
    }
    
    /**
     * Sets up click listeners for UI elements
     */
    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    /**
     * Updates the UI based on current game state
     * @param state Current game state
     */
    private fun updateUI(state: GameState) {
        // Update score
        binding.scoreText.text = "Score: ${state.score}"
        
        // Update timer
        binding.timerText.text = "Time: ${state.timeLeft}s"
        
        // Update round
        binding.roundText.text = "Round: ${state.currentRound}"
        
        // Update target color display
        binding.targetColorText.text = "Tap: ${getColorName(state.targetColor)}"
        binding.targetColorView.setBackgroundColor(android.graphics.Color.parseColor(state.targetColor))
        
        // Update tile colors
        updateTileColors()
        
        // Check if game is over
        if (!state.isGameActive && state.timeLeft == 0) {
            showGameOverScreen()
        }
    }
    
    /**
     * Updates the colors of all tiles in the grid
     */
    private fun updateTileColors() {
        val availableColors = GameConstants.GAME_COLORS.shuffled()
        
        tiles.forEachIndexed { index, tile ->
            val color = availableColors[index % availableColors.size]
            tile.setBackgroundColor(android.graphics.Color.parseColor(color))
            tile.tag = color // Store color for tap detection
        }
    }
    
    /**
     * Handles tile tap events
     * @param view The tapped tile view
     */
    fun onTileTapped(view: View) {
        if (!viewModel.gameState.value?.isGameActive!!) return
        
        val tileColor = view.tag as String
        viewModel.onTileTapped(tileColor)
        
        // Show visual feedback
        showTileFeedback(view, tileColor == viewModel.getTargetColor())
    }
    
    /**
     * Shows visual feedback for tile taps
     * @param view The tapped tile
     * @param isCorrect Whether the tap was correct
     */
    private fun showTileFeedback(view: View, isCorrect: Boolean) {
        val scale = if (isCorrect) 1.2f else 0.8f
        val color = if (isCorrect) android.graphics.Color.GREEN else android.graphics.Color.RED
        
        // Scale animation
        val scaleAnimator = ObjectAnimator.ofFloat(view, "scaleX", scale).apply {
            duration = GameConstants.ANIMATION_DURATION
        }
        val scaleAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", scale).apply {
            duration = GameConstants.ANIMATION_DURATION
        }
        
        // Color flash animation
        val colorAnimator = ObjectAnimator.ofArgb(view, "backgroundColor", 
            android.graphics.Color.parseColor(view.tag as String), color).apply {
            duration = GameConstants.ANIMATION_DURATION / 2
        }
        
        // Play animations
        AnimatorSet().apply {
            playTogether(scaleAnimator, scaleAnimatorY, colorAnimator)
            start()
        }
        
        // Reset after animation
        lifecycleScope.launch {
            delay(GameConstants.ANIMATION_DURATION)
            view.scaleX = 1f
            view.scaleY = 1f
            view.setBackgroundColor(android.graphics.Color.parseColor(view.tag as String))
        }
    }
    
    /**
     * Shows the game over screen with final score
     */
    private fun showGameOverScreen() {
        val (correctTaps, wrongTaps) = viewModel.getGameStats()
        
        val action = GameFragmentDirections.actionGameFragmentToGameOverFragment(
            finalScore = viewModel.getScore(),
            correctTaps = correctTaps,
            wrongTaps = wrongTaps,
            roundsPlayed = viewModel.getCurrentRound()
        )
        
        findNavController().navigate(action)
    }
    
    /**
     * Gets the human-readable name of a color
     * @param colorHex Hex color string
     * @return Color name
     */
    private fun getColorName(colorHex: String): String {
        return when (colorHex) {
            "#FF5722" -> "Red"
            "#4CAF50" -> "Green"
            "#2196F3" -> "Blue"
            "#FFC107" -> "Yellow"
            "#9C27B0" -> "Purple"
            "#FF9800" -> "Orange"
            "#795548" -> "Brown"
            "#607D8B" -> "Grey"
            else -> "Unknown"
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
