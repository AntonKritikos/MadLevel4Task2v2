package com.example.madlevel4task2v2

import android.content.Context

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(Game: Game) {
        gameDao.insertGame(Game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun countDraws(): Int {
        return gameDao.countDraws()
    }

    suspend fun countWins(): Int {
        return gameDao.countWins()
    }

    suspend fun countLosses(): Int {
        return gameDao.countLose()
    }
}