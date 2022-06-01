package com.example.wordledex

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.example.wordledex.database.WordleDexDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainModel(context: Context) {
    private val network = Network.getInstance(context)
    private val database = WordleDexDatabase.getInstance(context).database

    fun loadPokemonData(listener: Response.Listener<ArrayList<Pokemon>>, errorListener: Response.ErrorListener) {
        GlobalScope.launch(Dispatchers.Main) {
            val pokemonList = withContext(Dispatchers.IO){
                Log.d("APP-ACTION", "Loading pokémon data from the DB...")
                database.wordleDexDao().loadPokemonData() as ArrayList<Pokemon>
            }
            listener.onResponse(pokemonList)
        }
    }

    fun loadMissingPokemonData(listener: Response.Listener<ArrayList<Pokemon>>, errorListener: Response.ErrorListener) {
        GlobalScope.launch(Dispatchers.Main) {
            val pokemonList = withContext(Dispatchers.IO){
                Log.d("APP-ACTION", "Loading missing pokémon data from the DB...")
                database.wordleDexDao().loadMissingPokemonData() as ArrayList<Pokemon>
            }
            listener.onResponse(pokemonList)
        }
    }

    fun loadPlayerData(listener: Response.Listener<PlayerData>, errorListener: Response.ErrorListener) {
        GlobalScope.launch(Dispatchers.Main) {
            val playerData = withContext(Dispatchers.IO){
                Log.d("APP-ACTION", "Loading player data from the DB...")
                database.wordleDexDao().loadPlayerData()
            }
            listener.onResponse(playerData)
        }
    }

    fun savePokemonData(pokemonList: ArrayList<Pokemon>){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Log.d("APP-ACTION", "Saving pokémon data to DB!")
                database.wordleDexDao().insertPokemonData(pokemonList)
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

    fun getPokemon(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, number : Int){
        network.getPokemon({ listener.onResponse(it) },{errorListener.onErrorResponse(it)},number)
    }

    fun getPokemonSpecies(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonSpecies({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }

    /*
    fun getPokemonNormalSprite(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonNormalSprite({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }

    fun getPokemonShinySprite(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonShinySprite({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }

    fun getPokemonNormalArt(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonNormalArt({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }

    fun getPokemonShinyArt(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonShinyArt({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }

    fun getPokemonIconSprite(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonIconSprite({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }
    */
}