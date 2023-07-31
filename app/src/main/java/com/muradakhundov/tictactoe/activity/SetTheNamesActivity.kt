package com.muradakhundov.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.muradakhundov.tictactoe.databinding.ActivitySetTheNamesBinding
import com.muradakhundov.tictactoe.preferencemanager.PreferenceManager

class SetTheNamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetTheNamesBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySetTheNamesBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(this)

        var isMultiplayer = preferenceManager.getBoolean("isTwoPlayer")

        if (!isMultiplayer) {
            binding.playerSecondEditTxt.isEnabled = false
            binding.playerSecondEditTxt.alpha = 0.3f
            binding.playerSecondEditTxt.setText("Computer")
        }

        binding.startbtn.setOnClickListener {
            var firstPlayer = binding.playerFirstEditTxt.text.toString()
            var secondPlayer = binding.playerSecondEditTxt.text.toString()
            if (firstPlayer == "" && secondPlayer == "") {
                firstPlayer = "Player1"
                secondPlayer = "Player2"
            }
            Log.e("tag", "$firstPlayer , $secondPlayer")


            if (isMultiplayer){
                val intent = Intent(this, TicTacToePlayerActivity::class.java)
                intent.putExtra("Player1", firstPlayer)
                intent.putExtra("Player2", secondPlayer)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, TicTacToeComputerActivity::class.java)
                intent.putExtra("Player1", firstPlayer)
                intent.putExtra("Player2", "Computer")
                startActivity(intent)
            }
            finish()

        }



        setContentView(binding.root)
    }
}