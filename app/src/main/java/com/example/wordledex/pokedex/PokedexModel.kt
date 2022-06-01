package com.example.wordledex.pokedex

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.example.wordledex.database.Pokemon
import com.example.wordledex.database.WordleDexDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokedexModel(context: Context) {
    private val database = WordleDexDatabase.getInstance(context).database

    fun loadOwnedPokemonData(listener: Response.Listener<ArrayList<Pokemon>>, errorListener: Response.ErrorListener) {
        GlobalScope.launch(Dispatchers.Main) {
            val pokemonList = withContext(Dispatchers.IO){
                Log.d("APP-ACTION", "Loading owned pokémon data from the DB...")
                database.wordleDexDao().loadOwnedPokemonData() as ArrayList<Pokemon>
            }
            listener.onResponse(pokemonList)
        }
    }
}