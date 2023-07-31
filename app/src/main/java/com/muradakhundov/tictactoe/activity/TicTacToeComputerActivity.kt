package com.muradakhundov.tictactoe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.muradakhundov.tictactoe.Constants
import com.muradakhundov.tictactoe.R
import com.muradakhundov.tictactoe.databinding.ActivityTicTacToeBinding
import com.muradakhundov.tictactoe.model.Board
import com.muradakhundov.tictactoe.model.Cell
import com.muradakhundov.tictactoe.preferencemanager.PreferenceManager
import kotlin.random.Random

class TicTacToeComputerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicTacToeBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var boardCells: Array<Array<ImageButton>>
    var board = Board()
    var score1 = 0
    var score2 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initalizeButtons()
        setTheNames()

        if (binding.player1Name.text == "") {
            binding.player1Name.text = "Player1"
        }
        preferenceManager = PreferenceManager(this)
        binding.playAgain.setOnClickListener {
            board = Board()
            disableButtons(2)
            connectBoardToDesign()
            binding.supportWinChecker.visibility = View.INVISIBLE
        }
        binding.resetScore.setOnClickListener {
            binding.player1Score.text = "0"
            binding.player2Score.text = "0"
            binding.supportWinChecker.visibility = View.INVISIBLE
        }
    }


    private fun initalizeButtons() {
        boardCells = arrayOf(
            arrayOf(
                binding.firstCell,
                binding.secondCell,
                binding.thirdCell
            ),
            arrayOf(
                binding.fourthCell,
                binding.fifthCell,
                binding.sixthCell
            ),
            arrayOf(
                binding.seventhCell,
                binding.eighthCell,
                binding.ninthCell
            )
        )
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
            }
        }

    }

    private fun connectBoardToDesign() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.x)
                        boardCells[i][j]?.isEnabled = false
                    }

                    Board.COMPUTER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.o)
                        boardCells[i][j]?.isEnabled = false
                    }

                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    inner class CellClickListener(
        private val i: Int,
        private val j: Int
    ) : View.OnClickListener {
        override fun onClick(v: View?) {

            if (!board.isGameOver) {
                val cell = Cell(i, j)
                board.placeMove(cell, Board.PLAYER)

                if (!board.hasPlayerWon()) {
                    if (preferenceManager.getBoolean("isEasy")) {
                        if (board.availableCells.isNotEmpty()) {
                            val cCell =
                                board.availableCells[Random.nextInt(0, board.availableCells.size)]
                            board.placeMove(cCell, Board.COMPUTER)
                        }
                    } else {
                        board.minimax(0, Board.COMPUTER)
                        board.placeMove(board.computersMove!!, Board.COMPUTER)
                    }
                }

                connectBoardToDesign()
            }

            when {
                board.hasPlayerWon() -> {
                    score1++
                    binding.player1Score.text = score1.toString()
                    disableButtons(1)
                    binding.supportWinChecker.text = "You Won !"
                    binding.supportWinChecker.visibility = View.VISIBLE
                }

                board.hasComputerWon() -> {
                    score2++
                    binding.player2Score.text = score2.toString()
                    disableButtons(1)
                    binding.supportWinChecker.text = "Computer Won !"
                    binding.supportWinChecker.visibility = View.VISIBLE

                }

                board.isGameOver -> {
                    binding.supportWinChecker.text = "Draw!"
                    binding.supportWinChecker.visibility = View.VISIBLE
                }
            }

        }

    }

    fun setTheNames() {
        val user1: String? = intent.getStringExtra("Player1")
        val user2: String? = intent.getStringExtra("Player2")

        if (user1 != null || user2 != null) {
            binding.player1Name.text = user1
            binding.player2Name.text = user2
        } else {
            binding.player1Name.text = "Player1"
            binding.player2Name.text = "Computer"
        }
    }

    fun disableButtons(disable: Int) {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                if (disable == 1) {
                    boardCells[i][j]?.isEnabled = false
                } else if (disable == 2) {
                    boardCells[i][j]?.isEnabled = true
                }
            }
        }
    }

}

