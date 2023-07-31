package com.muradakhundov.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muradakhundov.tictactoe.databinding.ActivityMainBinding
import com.muradakhundov.tictactoe.preferencemanager.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(this)

        binding.playWplayer.setOnClickListener { preferenceManager.setBoolean("isTwoPlayer", true)
        startActivity(Intent(this, SetTheNamesActivity::class.java))}
        binding.playWcomp.setOnClickListener { preferenceManager.setBoolean("isTwoPlayer", false)
        startActivity(Intent(this, DifficultySelectionActivity::class.java))}
        setContentView(binding.root)
    }
}