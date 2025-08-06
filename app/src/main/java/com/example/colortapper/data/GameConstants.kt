package com.example.colortapper.data

/**
 * Constants used throughout the Color Tapper game
 */
object GameConstants {
    // Game configuration
    const val GRID_SIZE = 4 // 4x4 grid
    const val TIME_LIMIT_SECONDS = 30 // 30 seconds per round
    const val CORRECT_TAP_POINTS = 10
    const val WRONG_TAP_PENALTY = 5
    const val TARGET_COLOR_CHANGE_INTERVAL = 5000 // Change target color every 5 seconds
    
    // Colors for the game tiles
    val GAME_COLORS = listOf(
        "#FF5722", // Red
        "#4CAF50", // Green
        "#2196F3", // Blue
        "#FFC107", // Yellow
        "#9C27B0", // Purple
        "#FF9800", // Orange
        "#795548", // Brown
        "#607D8B"  // Blue Grey
    )
    
    // UI constants
    const val ANIMATION_DURATION = 200L // milliseconds
    const val SCORE_POPUP_DURATION = 1000L // milliseconds
}
