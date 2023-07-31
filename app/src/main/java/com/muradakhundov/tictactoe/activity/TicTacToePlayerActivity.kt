package com.muradakhundov.tictactoe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.muradakhundov.tictactoe.R
import com.muradakhundov.tictactoe.databinding.ActivityTicTacToeBinding
import com.muradakhundov.tictactoe.preferencemanager.PreferenceManager

class TicTacToePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicTacToeBinding
    private lateinit var preferenceManager: PreferenceManager
    private var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    private lateinit var buttonList: Array<ImageButton>
    var position = 0
    var user1Score: Int = 0
    var user2Score: Int = 0
    var userName1  :String? = "Player1"
    var userName2  :String?= "Player2"
    private var isPlayer1Turn = true
    var isMultiplayer = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityTicTacToeBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(this)

        buttonList = arrayOf(
            binding.firstCell,
            binding.secondCell,
            binding.thirdCell,
            binding.fourthCell,
            binding.fifthCell,
            binding.sixthCell,
            binding.seventhCell,
            binding.eighthCell,
            binding.ninthCell
        )
        isMultiplayer = preferenceManager.getBoolean("isTwoPlayer")

        setUpButton()

        setTheNames()
        binding.playAgain.setOnClickListener {
            playAgain()
            binding.supportWinChecker.visibility = View.INVISIBLE

        }

        binding.resetScore.setOnClickListener {
            user1Score = 0
            user2Score = 0
            binding.player1Score.text = user1Score.toString()
            binding.player2Score.text = user2Score.toString()
            binding.supportWinChecker.visibility = View.INVISIBLE

        }

        setContentView(binding.root)
    }


    fun setTheNames() {
        val user1: String? = intent.getStringExtra("Player1")
        val user2: String? = intent.getStringExtra("Player2")

        if (user1 != null || user2 != null) {
            binding.player1Name.text = user1
            binding.player2Name.text = user2
        } else {
            binding.player1Name.text = "Player1"
            binding.player2Name.text = "Player2"
        }
        userName1 = user1
        userName2 = user2

    }

    fun setUpButton() {

            buttonList.forEach { button ->

                button.setOnClickListener {
                    putXorO(button)
                    position = buttonList.indexOf(button)
                    if (isPlayer1Turn) {
                        board[position] = 1
                    } else {
                        board[position] = 2
                    }
                    Log.e("tag", board.contentToString())
                    checkWin()

                }
            }



    }

    fun putXorO(button: ImageButton) {
        if (isMultiplayer) {
            if (isPlayer1Turn) {
                button.setImageResource(R.drawable.x)
                isPlayer1Turn = false


            } else {
                button.setImageResource(R.drawable.o)
                isPlayer1Turn = true
            }
            button.isEnabled = false
        }
    }


    fun checkWin() {
        val combo = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columns
            listOf(0, 4, 8), listOf(2, 4, 6) // Diagonals
        )

        for (winState in combo) {
            val element1 = board[winState[0]]
            val element2 = board[winState[1]]
            val element3 = board[winState[2]]

            if (element1 != 0 && element1 == element2 && element2 == element3) {
                if (isPlayer1Turn) {
                    binding.supportWinChecker.text = "$userName2 Won !"
                    binding.supportWinChecker.visibility = View.VISIBLE
                    user2Score++
                    binding.player2Score.text = user2Score.toString()

                } else {
                    binding.supportWinChecker.text = "$userName1 Won !"
                    binding.supportWinChecker.visibility = View.VISIBLE
                    user1Score++
                    binding.player1Score.text = user1Score.toString()
                }
                disableAllCells()
                return
            }
        }


    }

    private fun disableAllCells() {
        buttonList.forEach { button ->
            button.isEnabled = false
        }
    }

    fun playAgain() {
        buttonList.forEach { button ->
            button.setImageResource(0)
            button.isEnabled = true
            isPlayer1Turn = true
            for (i in 0..8) {
                board[i] = 0
            }
        }
    }

}