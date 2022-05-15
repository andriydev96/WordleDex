package com.example.wordledex

import android.content.Context
import android.util.Log
import com.android.volley.Response
import javax.xml.transform.ErrorListener

class Model(context: Context) {
    private val network = Network.getInstance(context)

    fun getPokemon(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, number : Int){
        network.getPokemon({ listener.onResponse(it) },{errorListener.onErrorResponse(it)},number)
    }

    fun getPokemonSpecies(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon : Pokemon){
        network.getPokemonSpecies({ listener.onResponse(it) },{errorListener.onErrorResponse(it)}, pokemon)
    }
}