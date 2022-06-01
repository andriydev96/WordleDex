package com.example.wordledex

import android.util.Log
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainPresenter(val view: MainActivity, val mainModel: MainModel) {
    init {
        loadGameData()
    }

    var pokemonList = ArrayList<Pokemon>()
    var playerData : PlayerData? = null

    //Loads all of the game data from the database or downloads it and sets it up if it's the first time playing
    fun loadGameData(){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                mainModel.loadPokemonData({loadPokemonData(it)},{})
                mainModel.loadPlayerData({loadPlayerData(it)},{loadPlayerData(playerData)})
            }
        }
        playerData = PlayerData()
    }

    //Gets an incomplete list of information about each pokémon
    fun getPokemon(){
        for (i in 1..898)
            mainModel.getPokemon({addPokemonToList(it)}, {Log.d("ERROR POKEMON", "$i")}, i)
    }

    //Gets the remaining info of each pokémon
    fun getPokemonSpeciesInfo(){
        for (i in 1..898)
            mainModel.getPokemonSpecies({updatePokemonSpeciesData(it)},{Log.d("ERROR SPECIES INFO", "$i")}, pokemonList[i-1])
    }

    fun loadPokemonData(loadedData : ArrayList<Pokemon>){
        pokemonList = loadedData
        if (pokemonList.isNotEmpty() && pokemonList.size == 898) {
            Log.d("APP-ACTION", "Successfully got pokémon list from local database. $pokemonList")
        } else {
            getPokemon()
            Log.d("APP-ACTION", "Could not get pokémon list from local database. Downloading...")
        }
    }

    fun loadPlayerData(loadedData : PlayerData?){
        playerData = loadedData
        if (playerData != null) {
            Log.d("APP-ACTION", "Successfully got player data from local database. $playerData")
        } else {
            playerData = PlayerData()
            mainModel.savePlayerData(playerData!!)
            Log.d("APP-ACTION", "No player data found. Initializing data...")
        }
    }

    //Adds the pokémon to the pokemonList
    fun addPokemonToList(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        if (pokemonList.size == 898) {
            pokemonList.sortBy { it.dex }
            getPokemonSpeciesInfo()
        }
    }

    //Updates the corresponding Pokemon object of the pokemonList with its description and capture rate
    fun updatePokemonSpeciesData(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
        if (pokemon.dex == 898)
            mainModel.savePokemonData(pokemonList)
            //getPokemonNormalSprite()
    }

    fun launchGameActivity(){
        view.launchGameActivity(pokemonList[Random.nextInt(0,897)], playerData!!)
    }

    /*
    //Gets the normal sprite bitmap of each pokémon
    fun getPokemonNormalSprite(){
        for (i in 1..898)
            model.getPokemonNormalSprite({updatePokemonNormalSprite(it)},{Log.d("ERROR NORMAL SPRITE", "$i")}, pokemonList[i-1])
    }

    //Gets the shiny sprite bitmap of each pokémon
    fun getPokemonShinySprite(){
        for (i in 1..898)
            model.getPokemonShinySprite({updatePokemonShinySprite(it)},{Log.d("ERROR SHINY SPRITE", "$i")}, pokemonList[i-1])
    }

    //Gets the normal art bitmap of each pokémon
    fun getPokemonNormalArt(){
        for (i in 1..898)
            model.getPokemonNormalArt({updatePokemonNormalArt(it)},{Log.d("ERROR NORMAL ART", "$i")}, pokemonList[i-1])
    }

    //Gets the shiny art bitmap of each pokémon
    fun getPokemonShinyArt(){
        for (i in 1..898)
            model.getPokemonShinyArt({updatePokemonShinyArt(it)},{Log.d("ERROR SHINY ART", "$i")}, pokemonList[i-1])
    }

    //Gets the icon sprite bitmap of each pokémon
    fun getPokemonIconSprite(){
        for (i in 1..898)
            model.getPokemonIconSprite({updatePokemonIconSprite(it)},{Log.d("ERROR ICON", "$i")}, pokemonList[i-1])
    }
    */

    /*
    //Updates the corresponding Pokemon object of the pokemonList with its normal sprite bitmap
    fun updatePokemonNormalSprite(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
        if (pokemon.dex == 898) {
            getPokemonShinySprite()
        }
    }

    //Updates the corresponding Pokemon object of the pokemonList with its shiny sprite bitmap
    fun updatePokemonShinySprite(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
        if (pokemon.dex == 898) {
            getPokemonNormalArt()
        }
    }

    //Updates the corresponding Pokemon object of the pokemonList with its normal art bitmap
    fun updatePokemonNormalArt(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
        if (pokemon.dex == 898) {
            getPokemonShinyArt()
        }
    }

    //Updates the corresponding Pokemon object of the pokemonList with its shiny art bitmap
    fun updatePokemonShinyArt(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
        if (pokemon.dex == 898) {
            getPokemonIconSprite()
        }
    }

    //Updates the corresponding Pokemon object of the pokemonList with its icon sprite bitmap
    fun updatePokemonIconSprite(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
    }
    */
}

