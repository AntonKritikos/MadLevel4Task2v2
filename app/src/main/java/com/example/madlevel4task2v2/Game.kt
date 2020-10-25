package com.example.madlevel4task2v2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game_table")
class Game(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "player")
    var player: Short,

    @ColumnInfo(name = "computer")
    var computer: Short,

    @ColumnInfo(name = "date")
    val date: Date
)