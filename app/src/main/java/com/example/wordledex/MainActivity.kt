package com.example.wordledex

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.example.wordledex.game.GameActivity
import com.example.wordledex.pokedex.PokedexActivity

class MainActivity : AppCompatActivity() {
    lateinit var presenter : MainPresenter
    lateinit var imageViewLogo: ImageView
    lateinit var imageViewLogoProgress: ImageView
    lateinit var playButton: Button
    lateinit var buttonCollection: Button
    lateinit var buttonAchievements: Button
    lateinit var progressBar: ProgressBar
    lateinit var textViewProgress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        presenter = MainPresenter(this, MainModel(this))
        imageViewLogo = findViewById(R.id.imageViewLogo)
        imageViewLogoProgress = findViewById(R.id.imageViewLogoProgress)
        playButton = findViewById(R.id.buttonPlay)
        buttonAchievements = findViewById(R.id.buttonAchievements)
        buttonCollection = findViewById(R.id.buttonCollection)
        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.textViewProgress)

        playButton.setOnClickListener {
            presenter.launchGameActivity()
        }

        buttonCollection.setOnClickListener {
            launchPokedexActivity()
        }

        toggleInterface(false)
    }

    //Reloads game data in case it has been updated
    override fun onResume() {
        super.onResume()
        textViewProgress
        toggleInterface(false)
        presenter.loadGameData()
    }

    //Toggles the main menu and loading screen visibility
    fun toggleInterface(enabled: Boolean){
        if (enabled){
            imageViewLogoProgress.visibility = GONE
            progressBar.visibility = GONE
            textViewProgress.visibility = GONE
            imageViewLogo.visibility = VISIBLE
            playButton.visibility = VISIBLE
            buttonAchievements.visibility = VISIBLE
            buttonCollection.visibility = VISIBLE
        } else {
            imageViewLogoProgress.visibility = VISIBLE
            progressBar.visibility = VISIBLE
            textViewProgress.visibility = VISIBLE
            imageViewLogo.visibility = GONE
            playButton.visibility = GONE
            buttonAchievements.visibility = GONE
            buttonCollection.visibility = GONE
        }
    }

    //Updates progress bar
    fun updateProgressScreen(text: String, progress: Int){
        textViewProgress.text = text
        progressBar.progress += progress
        if (progressBar.progress >= progressBar.max){
            toggleInterface(true)
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