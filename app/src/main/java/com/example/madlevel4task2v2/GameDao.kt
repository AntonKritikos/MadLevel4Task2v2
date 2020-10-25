package com.example.madlevel4task2v2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM game_table WHERE result LIKE 'DRAW'")
    suspend fun countDraws(): Int

    @Query("SELECT COUNT(*) FROM game_table WHERE result LIKE 'WIN'")
    suspend fun countWins(): Int

    @Query("SELECT COUNT(*) FROM game_table WHERE result LIKE 'LOSE'")
    suspend fun countLose(): Int
}