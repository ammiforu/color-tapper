package com.example.colortapper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colortapper.data.GameConstants
import com.example.colortapper.data.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for the Color Tapper game
 * Handles game logic, state management, and background tasks
 */
class GameViewModel : ViewModel() {
    
    // Private mutable game state
    private val _gameState = MutableLiveData(GameState())
    
    // Public immutable game state for UI observation
    val gameState: LiveData<GameState> = _gameState
    
    // Timer job for the countdown
    private var timerJob: Job? = null
    
    // Color change job for target color updates
    private var colorChangeJob: Job? = null
    
    /**
     * Starts a new game
     */
    fun startGame() {
        _gameState.value = GameState().apply {
            isGameActive = true
        }
        startTimer()
        startColorChangeTimer()
    }
    
    /**
     * Stops the current game
     */
    fun stopGame() {
        timerJob?.cancel()
        colorChangeJob?.cancel()
        _gameState.value = _gameState.value?.apply {
            isGameActive = false
        }
    }
    
    /**
     * Restarts the game
     */
    fun restartGame() {
        stopGame()
        startGame()
    }
    
    /**
     * Handles tile tap events
     * @param tileColor The color of the tapped tile
     */
    fun onTileTapped(tileColor: String) {
        if (!_gameState.value?.isGameActive!!) return
        
        val isCorrect = tileColor == _gameState.value?.targetColor
        _gameState.value = _gameState.value?.apply {
            updateScore(isCorrect)
        }
        
        // Visual feedback could be added here
        if (isCorrect) {
            // Correct tap - could play a sound or show animation
        } else {
            // Wrong tap - could play a different sound or show error animation
        }
    }
    
    /**
     * Starts the countdown timer
     */
    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_gameState.value?.isGameActive == true && _gameState.value?.timeLeft!! > 0) {
                delay(1000) // Wait 1 second
                _gameState.value = _gameState.value?.apply {
                    if (!decreaseTime()) {
                        // Time's up - stop the game
                        stopGame()
                    }
                }
            }
        }
    }
    
    /**
     * Starts the color change timer
     */
    private fun startColorChangeTimer() {
        colorChangeJob = viewModelScope.launch {
            while (_gameState.value?.isGameActive == true) {
                delay(GameConstants.TARGET_COLOR_CHANGE_INTERVAL.toLong())
                _gameState.value = _gameState.value?.apply {
                    changeTargetColor()
                }
            }
        }
    }
    
    /**
     * Gets the current score
     * @return Current score
     */
    fun getScore(): Int {
        return _gameState.value?.score ?: 0
    }
    
    /**
     * Gets the time left
     * @return Time left in seconds
     */
    fun getTimeLeft(): Int {
        return _gameState.value?.timeLeft ?: 0
    }
    
    /**
     * Gets the target color
     * @return Target color as hex string
     */
    fun getTargetColor(): String {
        return _gameState.value?.targetColor ?: GameConstants.GAME_COLORS.first()
    }
    
    /**
     * Gets the current round
     * @return Current round number
     */
    fun getCurrentRound(): Int {
        return _gameState.value?.currentRound ?: 1
    }
    
    /**
     * Gets game statistics
     * @return Pair of correct taps and wrong taps
     */
    fun getGameStats(): Pair<Int, Int> {
        val state = _gameState.value
        return Pair(state?.correctTaps ?: 0, state?.wrongTaps ?: 0)
    }
    
    /**
     * Cleans up resources when ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        colorChangeJob?.cancel()
    }
}
