package com.example.wordledex.game

import android.graphics.Color
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat
import com.example.wordledex.R
import com.example.wordledex.database.Pokemon

class GamePresenter(private val view: GameActivity, val model: GameModel) {
    var updatedKeys = ""

    fun keyPressed(key : String, pokemon: Pokemon){
        model.updateGuessString(key, pokemon)

        var guess = model.currentGuess
        if (guess.length == pokemon.name!!.length)
            toggleGuessButton(true)
        else {
            toggleGuessButton(false)
            while (guess.length < pokemon.name!!.length) guess += "_"
        }
        view.setButtonText(guess)
    }

    fun guessButtonPressed(pokemon: Pokemon){
        if (model.currentGuess == pokemon.name){
            view.playerData.gamesPlayed += 1
            view.playerData.gamesWon += 1
            if (model.life == 5) view.playerData.perfectWins += 1
            if (!pokemon.caught) view.playerData.dexEntries += 1
            pokemon.caught = true
            model.savePokemonData(pokemon)
            model.savePlayerData(view.playerData)
            view.gameOver(model.life, pokemon)
            Log.d("APP-ACTION", "YOU WIN!!!")
        } else {
            model.life -= 1
            view.setLife(model.life)
            view.setWhoText(model.getColoredString(pokemon))
            updateKeyBoard(pokemon)
            view.clearButtonText()
            model.currentGuess = ""
            toggleGuessButton(false)
            if (model.life == 2) {
                var lifeBarDrawable = view.lifeBar.progressDrawable
                lifeBarDrawable = DrawableCompat.wrap(lifeBarDrawable)
                DrawableCompat.setTint(lifeBarDrawable, Color.parseColor("#F4A900"))
                view.lifeBar.progressDrawable = lifeBarDrawable
            } else if (model.life == 1){
                var lifeBarDrawable = view.lifeBar.progressDrawable
                lifeBarDrawable = DrawableCompat.wrap(lifeBarDrawable)
                DrawableCompat.setTint(lifeBarDrawable, Color.RED)
                view.lifeBar.progressDrawable = lifeBarDrawable
            } else if (model.life == 0){
                view.playerData.gamesPlayed += 1
                view.playerData.gamesLost += 1
                model.savePlayerData(view.playerData)
                view.gameOver(model.life, pokemon)
                Log.d("APP-ACTION", "YOU LOST!!!")
            }
            showHint(pokemon)
        }
    }

    fun toggleGuessButton(enabled : Boolean){
        view.buttonGuess.isClickable = enabled
        if (enabled) {
            var buttonDrawable = view.buttonGuess.background
            buttonDrawable = DrawableCompat.wrap(buttonDrawable)
            DrawableCompat.setTint(buttonDrawable, Color.parseColor("#FF4CAF50"))
            view.buttonGuess.background = buttonDrawable
        } else {
            var buttonDrawable = view.buttonGuess.background
            buttonDrawable = DrawableCompat.wrap(buttonDrawable)
            DrawableCompat.setTint(buttonDrawable, Color.parseColor("#FF292424"))
            view.buttonGuess.background = buttonDrawable
        }
    }

    fun showHint(pokemon: Pokemon){
        when (model.life){
            4 -> showType(view.imageViewType1, pokemon.primaryType.toString())
            3 -> showType(view.imageViewType2, pokemon.secondaryType.toString())
            2 -> view.setGeneration("Gen: -${pokemon.generation}-")
            1 -> view.silhouetteImageView.setColorFilter(Color.parseColor("#C8000000"))
        }
    }

    fun showType(typePlate : ImageView, pokemonType: String){
        when (pokemonType) {
            "BUG" -> typePlate.setImageResource(R.drawable.bug)
            "DARK" -> typePlate.setImageResource(R.drawable.dark)
            "DRAGON" -> typePlate.setImageResource(R.drawable.dragon)
            "ELECTRIC" -> typePlate.setImageResource(R.drawable.electric)
            "FAIRY" -> typePlate.setImageResource(R.drawable.fairy)
            "FIGHTING" -> typePlate.setImageResource(R.drawable.fighting)
            "FIRE" -> typePlate.setImageResource(R.drawable.fire)
            "FLYING" -> typePlate.setImageResource(R.drawable.flying)
            "GHOST" -> typePlate.setImageResource(R.drawable.ghost)
            "GRASS" -> typePlate.setImageResource(R.drawable.grass)
            "GROUND" -> typePlate.setImageResource(R.drawable.ground)
            "ICE" -> typePlate.setImageResource(R.drawable.ice)
            "NORMAL" -> typePlate.setImageResource(R.drawable.normal)
            "POISON" -> typePlate.setImageResource(R.drawable.poison)
            "PSYCHIC" -> typePlate.setImageResource(R.drawable.psychic)
            "ROCK" -> typePlate.setImageResource(R.drawable.rock)
            "STEEL" -> typePlate.setImageResource(R.drawable.steel)
            "WATER" -> typePlate.setImageResource(R.drawable.water)
            else -> typePlate.setImageResource(R.drawable.none)
        }
    }

    fun updateKeyBoard(pokemon: Pokemon){

        for (i in 1..model.currentGuess.length){
            if (model.currentGuess.substring(i - 1, i) == pokemon.name!!.substring(i - 1, i) && !updatedKeys.contains(model.currentGuess.substring(i - 1, i))){
                updateKey(model.currentGuess.substring(i - 1, i), Color.parseColor("#FF409A2F"))
                updatedKeys += model.currentGuess.substring(i - 1, i)
            } else if (pokemon.name!!.contains(model.currentGuess.substring(i - 1, i)) && !updatedKeys.contains(model.currentGuess.substring(i - 1, i))){
                updateKey(model.currentGuess.substring(i - 1, i), Color.parseColor("#FFD89D25"))
            } else if (!updatedKeys.contains(model.currentGuess.substring(i - 1, i))){
                updateKey(model.currentGuess.substring(i - 1, i), Color.parseColor("#C8000000"))
            }
        }
    }

    private fun updateKey(key : String, color: Int){
        val keyView = when (key){
            "A" -> view.buttonA
            "B" -> view.buttonB
            "C" -> view.buttonC
            "D" -> view.buttonD
            "E" -> view.buttonE
            "F" -> view.buttonF
            "G" -> view.buttonG
            "H" -> view.buttonH
            "I" -> view.buttonI
            "J" -> view.buttonJ
            "K" -> view.buttonK
            "L" -> view.buttonL
            "M" -> view.buttonM
            "N" -> view.buttonN
            "O" -> view.buttonO
            "P" -> view.buttonP
            "Q" -> view.buttonQ
            "R" -> view.buttonR
            "S" -> view.buttonS
            "T" -> view.buttonT
            "U" -> view.buttonU
            "V" -> view.buttonV
            "W" -> view.buttonW
            "Y" -> view.buttonY
            "X" -> view.buttonX
            "Z" -> view.buttonZ
            "-" -> view.buttonDash
            else -> view.buttonComa
        }

        if (color == Color.parseColor("#C8000000")) {
            keyView.isEnabled = false
            keyView.setBackgroundColor(color)
        } else {
            keyView.setBackgroundColor(color)
        }
    }
}