package com.example.wordledex

import android.util.Log
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor
import kotlin.random.Random

class MainPresenter(private val view: MainActivity, private val model: MainModel) {
    var pokemonList = ArrayList<Pokemon>()
    var missingPokemonList =  ArrayList<Pokemon>()
    var playerData : PlayerData? = null

    //Loads all of the game data from the database or downloads it and sets it up if it's the first time playing
    fun loadGameData(){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                model.loadPokemonData({
                    loadPokemonData(it)
                    if (it.isNotEmpty()) view.updateProgressScreen("Loading...", 898)}, {view.toast("Error: couldn't load pokémon list from local DB.")})
                model.loadMissingPokemonData({
                    loadMissingPokemonData(it)
                    if (it.isNotEmpty()) view.updateProgressScreen("Loading...", 898)},{view.toast("Error: couldn't load pokémon list from local DB.")})
                model.loadPlayerData({ loadPlayerData(it) },{loadPlayerData(playerData)})
            }
        }
        playerData = PlayerData()
    }

    //Gets an incomplete list of information about each pokémon
    fun getPokemon(){
        for (i in 1..898)
            model.getPokemon({
                addPokemonToList(it)
                view.updateProgressScreen("Downloading pokémon data ${floor((i/2).toDouble()).toInt()}/898", 1)},
                {view.textViewProgress.text = "ERROR: couldn't download pokémon data from the internet. Please, check your connection and restart the app."}, i)
    }

    //Gets the remaining info of each pokémon
    private fun getPokemonSpeciesInfo(){
        for (i in 1..898)
            model.getPokemonSpecies({
                updatePokemonSpeciesData(it)
                view.updateProgressScreen("Downloading pokémon data ${898/2 + floor((i/2).toDouble()).toInt()}/898", 1) },
                {view.textViewProgress.text = "ERROR: couldn't download pokémon data from the internet. Please, check your connection and restart the app."}, pokemonList[i-1])
    }

    //If not empty, stores the loaded pokémon list in the variable. Else, downloads it.
    private fun loadPokemonData(loadedData : ArrayList<Pokemon>){
        pokemonList = loadedData
        if (pokemonList.isNotEmpty() && pokemonList.size == 898) {
            Log.d("APP-ACTION", "Successfully got pokémon list from local database. $pokemonList")
        } else {
            getPokemon()
            Log.d("APP-ACTION", "Could not get pokémon list from local database. Downloading...")
        }
    }

    //Stores the list of the pokémon that the player is missing.
    private fun loadMissingPokemonData(loadedData : ArrayList<Pokemon>){
        missingPokemonList = loadedData
        if (missingPokemonList.isNotEmpty()) {
            Log.d("APP-ACTION", "Successfully got missing pokémon list from local database. $missingPokemonList")
        } else {
            Log.d("APP-ACTION", "No missing pokémon have been found!")
        }
    }

    //Stores the loaded player data. If no data was stored, initializes player data and saves it.
    private fun loadPlayerData(loadedData : PlayerData?){
        playerData = loadedData
        if (playerData != null) {
            Log.d("APP-ACTION", "Successfully got player data from local database. $playerData")
        } else {
            playerData = PlayerData()
            model.savePlayerData(playerData!!)
            Log.d("APP-ACTION", "No player data found. Initializing data...")
        }
    }

    //Adds the pokémon to the pokemonList
    private fun addPokemonToList(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        if (pokemonList.size == 898) {
            pokemonList.sortBy { it.dex }
            getPokemonSpeciesInfo()
        }
    }

    //Updates the corresponding Pokemon object of the pokemonList with its description and capture rate
    private fun updatePokemonSpeciesData(pokemon: Pokemon) {
        pokemonList[pokemon.dex-1] = pokemon
        if (pokemon.dex == 898)
            model.savePokemonData(pokemonList)
            //getPokemonNormalSprite()
    }

    //Launches game activity -> 50% chances to find a random pokémon and 50% chances to find a pokémon the player does not have yet.
    fun launchGameActivity(){
        if (missingPokemonList.isEmpty()){
            view.launchGameActivity(pokemonList[Random.nextInt(0,897)], playerData!!)
        } else {
            when (Random.nextInt(0,1)){
                0 -> view.launchGameActivity(pokemonList[Random.nextInt(0,897)], playerData!!)
                else -> view.launchGameActivity(missingPokemonList[Random.nextInt(0,missingPokemonList.size-1)], playerData!!)
            }
        }
    }
}

