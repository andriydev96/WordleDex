package com.example.wordledex.game

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.core.text.toSpannable
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.example.wordledex.database.WordleDexDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameModel(context: Context) {
    private val database = WordleDexDatabase.getInstance(context).database
    var currentGuess = ""
    var life = 5

    fun updateGuessString(key: String, pokemon: Pokemon){
        if (key == "DELETE") currentGuess = currentGuess.dropLast(1)
        else if (currentGuess.length < pokemon.name!!.length)
            currentGuess += key
    }

    fun getColoredString(pokemon: Pokemon) : SpannableString {
        val coloredString = SpannableString("It was not $currentGuess!")
        for (i in 1..currentGuess.length){
            if (currentGuess.substring(i - 1, i) == pokemon.name!!.substring(i - 1, i)){
                coloredString.setSpan(BackgroundColorSpan(Color.parseColor("#2cab18")), i + 10, i + 11, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            } else if (pokemon.name!!.contains(currentGuess.substring(i - 1, i))){
                coloredString.setSpan(BackgroundColorSpan(Color.parseColor("#e0e324")), i + 10, i + 11, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            } else {
                coloredString.setSpan(BackgroundColorSpan(Color.parseColor("#8f282b")), i + 10, i + 11, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
        return coloredString
    }

    fun savePokemonData(pokemon: Pokemon){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Log.d("APP-ACTION", "Saving pokémon data to DB!")
                database.wordleDexDao().insertPokemonData(pokemon)
            }
        }
    }

    fun savePlayerData(playerData: PlayerData){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Log.d("APP-ACTION", "Saving player data to DB!")
                database.wordleDexDao().insertPlayerData(playerData)
            }
        }
    }
}