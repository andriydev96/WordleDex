package com.example.wordledex.achievements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordledex.R
import com.example.wordledex.database.PlayerData

class AchievementsActivity : AppCompatActivity() {
    lateinit var playerData : PlayerData
    lateinit var presenter: AchievementsPresenter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
        supportActionBar!!.hide()

        playerData = intent.getParcelableExtra<PlayerData>(PLAYER_DATA)!!
        presenter = AchievementsPresenter(this, AchievementsModel())
    }

    //RecyclerView function
    fun displayAchievements(achievementList: ArrayList<Achievement>){
        recyclerView = findViewById(R.id.recyclerViewAchievements)
        if (recyclerView.adapter == null) {
            recyclerView.also {
                val adapter = AchievementAdapter(achievementList)
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this)
            }
        } else
            recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object{
        private const val PLAYER_DATA = "PLAYER_DATA"
    }
}