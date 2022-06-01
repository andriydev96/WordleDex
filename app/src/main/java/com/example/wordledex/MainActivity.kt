package com.example.wordledex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.example.wordledex.game.GameActivity
import com.example.wordledex.pokedex.PokedexActivity

class MainActivity : AppCompatActivity() {
    lateinit var presenter : MainPresenter
    lateinit var playButton: Button
    lateinit var buttonCollection: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        presenter = MainPresenter(this, MainModel(this))
        playButton = findViewById(R.id.buttonPlay)
        buttonCollection = findViewById(R.id.buttonCollection)

        playButton.setOnClickListener {
            presenter.launchGameActivity()
        }

        buttonCollection.setOnClickListener {
            launchPokedexActivity()
        }
    }

    //Launches the game activity
    fun launchGameActivity(pokemon: Pokemon, playerData: PlayerData){
        val intent = Intent(this, GameActivity::class.java).also {
            it.putExtra(POKEMON_DATA, pokemon)
            it.putExtra(PLAYER_DATA, playerData)
        }
        startActivity(intent)
    }

    //Launches the pokedex activity
    fun launchPokedexActivity(){
        val intent = Intent(this, PokedexActivity::class.java)
        startActivity(intent)
    }

    companion object{
        private const val POKEMON_DATA = "POKEMON_DATA"
        private const val PLAYER_DATA = "PLAYER_DATA"
    }
}