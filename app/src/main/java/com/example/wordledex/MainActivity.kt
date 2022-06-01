package com.example.wordledex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.example.wordledex.game.GameActivity

class MainActivity : AppCompatActivity() {
    lateinit var presenter : MainPresenter
    lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        presenter = MainPresenter(this, MainModel(this))
        playButton = findViewById(R.id.buttonPlay)

        playButton.setOnClickListener {
            presenter.launchGameActivity()
        }
    }

    //Launches the game activity
    fun launchGameActivity(pokemon: Pokemon, playerData: PlayerData){
        val intent = Intent(this, GameActivity::class.java).also {
            it.putExtra("POKEMON_DATA", pokemon)
            it.putExtra("PLAYER_DATA", playerData)
        }
        startActivity(intent)
    }
}