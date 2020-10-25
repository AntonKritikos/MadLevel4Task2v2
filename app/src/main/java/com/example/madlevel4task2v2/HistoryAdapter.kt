package com.example.madlevel4task2v2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game.view.*

class HistoryAdapter(private val games: List<Game>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(game: Game) {
            when (game.result) {
                itemView.context.getString(R.string.win) -> {
                    itemView.tvResult.text = itemView.context.getString(R.string.result_win)
                }
                itemView.context.getString(R.string.lose) -> {
                    itemView.tvResult.text = itemView.context.getString(R.string.result_lose)
                }
                else -> {
                    itemView.tvResult.text = itemView.context.getString(R.string.result_draw)
                }
            }
            itemView.tvTime.text = game.date.toString()
            when (RPSEnum.values()[game.player.toInt()]) {
                RPSEnum.ROCK -> itemView.player.setImageResource(R.drawable.rock)
                RPSEnum.PAPER -> itemView.player.setImageResource(R.drawable.paper)
                RPSEnum.SCISSORS -> itemView.player.setImageResource(R.drawable.scissors)
            }
            when (RPSEnum.values()[game.computer.toInt()]) {
                RPSEnum.ROCK -> itemView.computer.setImageResource(R.drawable.rock)
                RPSEnum.PAPER -> itemView.computer.setImageResource(R.drawable.paper)
                RPSEnum.SCISSORS -> itemView.computer.setImageResource(R.drawable.scissors)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_game, parent, false))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(games[position])
    }
}