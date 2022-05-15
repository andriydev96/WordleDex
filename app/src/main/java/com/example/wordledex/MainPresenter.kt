package com.example.wordledex

import android.util.Log

class MainPresenter(val view: MainActivity, val model: Model) {
    init {
        getPokemon()
    }

    val pokemonList = ArrayList<Pokemon>()
    val pokemonFullData = ArrayList<Pokemon>()

    //Gets an incomplete list of information about each pokémon
    fun getPokemon(){
        for (i in 1..898)
            model.getPokemon({addPokemonToList(it)},{getPokemon()}, i)
    }

    //Gets the remaining info of each pokémon
    fun getPokemonSpeciesInfo(){
        for (i in 1..898)
            model.getPokemonSpecies({addPokemonFullDataToList(it)},{getPokemon()}, pokemonList[i-1])
    }

    //Adds the pokémon to list
    fun addPokemonToList(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        if (pokemonList.size == 898) {
            pokemonList.sortBy { it.dex }
            getPokemonSpeciesInfo()
        }
    }

    //Adds the pokémon to the full data list
    fun addPokemonFullDataToList(pokemon: Pokemon) {
        pokemonFullData.add(pokemon)
        if (pokemonList.size == 898) {
            pokemonFullData.sortBy { it.dex }
            for (i in pokemonFullData.indices){
                Log.d("FULL DATA", pokemonFullData[i].toString())
            }
        }
    }
}
