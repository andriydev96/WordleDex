package com.example.wordledex.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.wordledex.R
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.squareup.picasso.Picasso

lateinit var pokemon: Pokemon
lateinit var playerData: PlayerData

lateinit var silhouetteImageView: ImageView

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_activitty)
        supportActionBar!!.hide()

        pokemon = intent.getParcelableExtra<Pokemon>("POKEMON_DATA")!!
        playerData = intent.getParcelableExtra<PlayerData>("PLAYER_DATA")!!

        silhouetteImageView = findViewById(R.id.silhouetteImageView)
        Picasso.get().load(pokemon.artNormalURL).into(silhouetteImageView)

        Log.d("APP-ACTION", "$pokemon")
    }
}