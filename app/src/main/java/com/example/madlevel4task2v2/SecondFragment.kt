package com.example.madlevel4task2v2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val games = arrayListOf<Game>()
    private val historyAdapter = HistoryAdapter(games)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        initViews()
    }

    private fun initViews() {
        rvHistory.layoutManager = GridLayoutManager(context, 1)
        rvHistory.adapter = historyAdapter

        getHistory()
    }

    private fun getHistory() {
        mainScope.launch {
            val list = withContext(Dispatchers.IO) {
                gameRepository.getAllGames().sortedByDescending { it.date }
            }
            this@SecondFragment.games.clear()
            this@SecondFragment.games.addAll(list)
            this@SecondFragment.historyAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.btnDeleteAllGames -> {
                clearHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames();
            }
            getHistory()
        }
    }

}