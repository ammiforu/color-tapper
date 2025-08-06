package com.example.colortapper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.colortapper.databinding.ActivityMainBinding

/**
 * Main activity for the Color Tapper game
 * Manages navigation between fragments and sets up the action bar
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        
        // Set up navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Configure AppBar for navigation
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.startFragment,
                R.id.gameFragment,
                R.id.gameOverFragment
            )
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        
        // Hide action bar for start screen
        supportActionBar?.hide()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
