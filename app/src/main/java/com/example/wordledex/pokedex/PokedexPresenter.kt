package com.example.wordledex.pokedex

import android.util.Log
import com.example.wordledex.database.Pokemon

class PokedexPresenter(private val view: PokedexActivity, val model: PokedexModel) {
    init {
        model.loadOwnedPokemonData({loadOwnedPokemonData(it)},{})
    }

    var ownedPokemonList =  ArrayList<Pokemon>()

    //Loads the list of pokémon that the player has caught from the database and passes it to the RecyclerView
    private fun loadOwnedPokemonData(loadedData : ArrayList<Pokemon>){
        ownedPokemonList = loadedData
        if (ownedPokemonList.isNotEmpty()) {
            view.displayPokedexEntries(ownedPokemonList)
            Log.d("APP-ACTION", "Successfully got owned pokémon list from local database. $ownedPokemonList")
        } else {
            Log.d("APP-ACTION", "No owned pokémon have been found...")
        }
    }
}