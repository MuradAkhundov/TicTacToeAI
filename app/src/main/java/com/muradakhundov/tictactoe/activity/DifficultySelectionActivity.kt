package com.muradakhundov.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muradakhundov.tictactoe.R
import com.muradakhundov.tictactoe.databinding.ActivityDifficultySelectionBinding
import com.muradakhundov.tictactoe.preferencemanager.PreferenceManager

class DifficultySelectionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDifficultySelectionBinding
    private lateinit var preferenceManager : PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDifficultySelectionBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(this)

        binding.easyButton.setOnClickListener {
            preferenceManager.setBoolean("isEasy",true)
            startActivity(Intent(this,SetTheNamesActivity::class.java))
            finish()
        }
        binding.hardButton.setOnClickListener {
            preferenceManager.setBoolean("isEasy",false)
            startActivity(Intent(this,SetTheNamesActivity::class.java))
            finish()
        }






        setContentView(binding.root)
    }
}