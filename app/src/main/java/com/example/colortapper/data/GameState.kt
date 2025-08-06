package com.example.colortapper.data

/**
 * Data class representing the current state of the Color Tapper game
 */
data class GameState(
    var score: Int = 0,
    var timeLeft: Int = GameConstants.TIME_LIMIT_SECONDS,
    var isGameActive: Boolean = false,
    var targetColor: String = GameConstants.GAME_COLORS.first(),
    var currentRound: Int = 1,
    var correctTaps: Int = 0,
    var wrongTaps: Int = 0
) {
    /**
     * Resets the game state to initial values
     */
    fun reset() {
        score = 0
        timeLeft = GameConstants.TIME_LIMIT_SECONDS
        isGameActive = false
        currentRound = 1
        correctTaps = 0
        wrongTaps = 0
        // Select a random target color
        targetColor = GameConstants.GAME_COLORS.random()
    }
    
    /**
     * Updates the score based on tap result
     * @param isCorrect true if the tap was correct, false otherwise
     */
    fun updateScore(isCorrect: Boolean) {
        if (isCorrect) {
            score += GameConstants.CORRECT_TAP_POINTS
            correctTaps++
        } else {
            score = maxOf(0, score - GameConstants.WRONG_TAP_PENALTY)
            wrongTaps++
        }
    }
    
    /**
     * Changes the target color to a random one different from current
     */
    fun changeTargetColor() {
        val availableColors = GameConstants.GAME_COLORS.filter { it != targetColor }
        targetColor = availableColors.random()
    }
    
    /**
     * Decreases time left by 1 second
     * @return true if time is still left, false if time is up
     */
    fun decreaseTime(): Boolean {
        timeLeft--
        return timeLeft > 0
    }
    
    /**
     * Starts a new round
     */
    fun startNewRound() {
        currentRound++
        timeLeft = GameConstants.TIME_LIMIT_SECONDS
        changeTargetColor()
    }
}
