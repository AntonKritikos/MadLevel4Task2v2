package com.example.madlevel4task2v2

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private lateinit var gameRepository: GameRepository;
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        drawScore()

        ivRockClick.setOnClickListener{
            addGame(RPSEnum.ROCK)
        }
        ivPaperClick.setOnClickListener{
            addGame(RPSEnum.PAPER)
        }
        ivScissorClick.setOnClickListener{
            addGame(RPSEnum.SCISSORS)
        }
    }

    private fun drawScore() {
        mainScope.launch {
            val draws = withContext(Dispatchers.IO) {
                gameRepository.countDraws()
            }
            val wins = withContext(Dispatchers.IO) {
                gameRepository.countWins()
            }
            val losses = withContext(Dispatchers.IO) {
                gameRepository.countLosses()
            }
            tvResult.text = getString(R.string.statistic_score, wins, draws, losses)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addGame(playerMove: RPSEnum) {
        val computerMove = generateComputerMove()
        val result = calculateMatch(playerMove,computerMove);

        mainScope.launch {
            val game = Game(
                result = result,
                computer = computerMove.ordinal.toShort(),
                player = playerMove.ordinal.toShort(),
                date = Date()
            )
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
        drawUI(playerMove,computerMove,result)
        drawScore()
    }

    private fun generateComputerMove(): RPSEnum {
        return RPSEnum.values()[(0..2).random()]
    }

    private fun calculateMatch(playerMove: RPSEnum, computerMove: RPSEnum): String {
        return if (playerMove == computerMove) {
            getString(R.string.draw)
        }
        else if (
            (playerMove == RPSEnum.ROCK && computerMove == RPSEnum.SCISSORS) ||
            (playerMove == RPSEnum.SCISSORS && computerMove == RPSEnum.PAPER) ||
            (playerMove == RPSEnum.PAPER && computerMove == RPSEnum.ROCK)
                ) {
            getString(R.string.win)
        }
        else {
            getString(R.string.lose)
        }
    }

    private fun drawUI(playerMove: RPSEnum, computerMove: RPSEnum, result: String) {
        when (result) {
            getString(R.string.win) -> {
                tvWinner.text = getString(R.string.result_win)
            }
            getString(R.string.lose) -> {
                tvWinner.text = getString(R.string.result_lose)
            }
            else -> {
                tvWinner.text = getString(R.string.result_draw)
            }
        }
        when (playerMove) {
            RPSEnum.ROCK -> computer.setImageResource(R.drawable.rock)
            RPSEnum.PAPER -> computer.setImageResource(R.drawable.paper)
            RPSEnum.SCISSORS -> computer.setImageResource(R.drawable.scissors)
        }
        when (computerMove) {
            RPSEnum.ROCK -> player.setImageResource(R.drawable.rock)
            RPSEnum.PAPER -> player.setImageResource(R.drawable.paper)
            RPSEnum.SCISSORS -> player.setImageResource(R.drawable.scissors)
        }
    }
}