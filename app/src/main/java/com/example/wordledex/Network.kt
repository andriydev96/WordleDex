package com.example.wordledex

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.wordledex.database.Pokemon
import org.json.JSONObject
import java.util.*

private const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"
private const val POKEMON_SPECIES_URL = "https://pokeapi.co/api/v2/pokemon-species/"

class Network private constructor(context: Context) {
    companion object : SingletonHolder<Network, Context>(::Network)

    open class SingletonHolder<out T, in A>
    (private val constructor: (A) -> T) {
        @Volatile
        private var instance: T? = null
        fun getInstance(arg: A): T =
                instance ?: synchronized(this) {
                    instance ?: constructor(arg).also { instance = it }
                }
    }

    private val queue = Volley.newRequestQueue(context)

    //Gets the name, height, weight, types and sprite URLs of a pokémon given its pokédex number
    fun getPokemon(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, number: Int) {
        val request = JsonObjectRequest(
                Request.Method.GET,
                POKEMON_URL + number,
                null,
                { response ->
                    val pokemon = Pokemon()
                    pokemon.dex = number
                    pokemon.name = response.getString("name").toUpperCase(Locale.ROOT)
                    if (pokemon.name == "MINIOR-RED-METEOR") pokemon.name = "MINIOR"
                    pokemon.height = response.getString("height").toFloat() / 10F
                    pokemon.weight = response.getString("weight").toFloat() / 10F

                    val typesArray = response.getJSONArray("types")
                    for (i in 0 until typesArray.length()) {
                        val typeSlot = typesArray[i] as JSONObject
                        if (i == 0) {
                            pokemon.primaryType = typeSlot.getJSONObject("type").getString("name").toUpperCase(Locale.ROOT)
                        } else {
                            pokemon.secondaryType = typeSlot.getJSONObject("type").getString("name").toUpperCase(Locale.ROOT)
                        }
                    }

                    val spritesObject = response.getJSONObject("sprites")
                    pokemon.spriteNormalURL = spritesObject.getString("front_default")
                    pokemon.spriteShinyURL = spritesObject.getString("front_shiny")
                    val artObject = spritesObject.getJSONObject("other").getJSONObject("home")
                    pokemon.artNormalURL = artObject.getString("front_default")
                    pokemon.artShinyURL = artObject.getString("front_shiny")
                    val iconObject = spritesObject.getJSONObject("versions").getJSONObject("generation-viii").getJSONObject("icons")
                    pokemon.iconURL = iconObject.getString("front_default")

                    listener.onResponse(pokemon)
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download $number's pokémon data."))
                }
        )
        queue.add(request)
    }

    //Gets the capture rate and description of a pokémon given its pokédex number
    fun getPokemonSpecies(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon: Pokemon) {
        val request = JsonObjectRequest(
                Request.Method.GET,
                POKEMON_SPECIES_URL + pokemon.dex,
                null,
                { response ->
                    pokemon.captureRate = response.getInt("capture_rate")
                    val descriptionsArray = response.getJSONArray("flavor_text_entries")
                    for (i in 0 until descriptionsArray.length()) {
                        val descriptionSlot = descriptionsArray[i] as JSONObject
                        if (descriptionSlot.getJSONObject("language").getString("name") == "en") {
                            pokemon.description = descriptionSlot.getString("flavor_text").replace("\n", " ")
                            break
                        }
                    }

                    listener.onResponse(pokemon)
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download ${pokemon.dex}'s pokémon species data."))
                }
        )
        queue.add(request)
    }

    /*
    //Downloads the normal sprite of a given pokémon as a bitmap
    fun getPokemonNormalSprite(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon: Pokemon){
        val request = ImageRequest(
                pokemon.spriteNormalURL,
                {
                    pokemon.spriteNormal = it
                    listener.onResponse(pokemon)
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download ${pokemon.dex}'s normal sprite."))
                }
        )
        queue.add(request)
    }

    //Downloads the shiny sprite of a given pokémon as a bitmap
    fun getPokemonShinySprite(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon: Pokemon){
        val request = ImageRequest(
                pokemon.spriteShinyURL,
                {
                    pokemon.spriteShiny = it
                    listener.onResponse(pokemon)
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download ${pokemon.dex}'s shiny sprite."))
                }
        )
        queue.add(request)
    }

    //Downloads the normal art of a given pokémon as a bitmap
    fun getPokemonNormalArt(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon: Pokemon){
        if (pokemon.artNormalURL == "null") pokemon.artNormalURL = pokemon.spriteNormalURL
        val request = ImageRequest(
                pokemon.artNormalURL,
                {
                    pokemon.artNormal = it
                    listener.onResponse(pokemon)
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download ${pokemon.dex}'s normal art."))
                }
        )
        queue.add(request)
    }

    //Downloads the shiny art of a given pokémon as a bitmap
    fun getPokemonShinyArt(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon: Pokemon){
        if (pokemon.artShinyURL == "null") pokemon.artShinyURL = pokemon.spriteShinyURL
        val request = ImageRequest(
                pokemon.artShinyURL,
                {
                    pokemon.artShiny = it
                    listener.onResponse(pokemon)
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download ${pokemon.dex}'s shiny art."))
                }
        )
        queue.add(request)
    }

    //Downloads the icon sprite of a given pokémon as a bitmap
    fun getPokemonIconSprite(listener: Response.Listener<Pokemon>, errorListener: Response.ErrorListener, pokemon: Pokemon){
        val request = ImageRequest(
                pokemon.iconURL,
                {
                    pokemon.icon = it
                    listener.onResponse(pokemon)
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Couldn't download ${pokemon.dex}'s icon sprite."))
                }
        )
        queue.add(request)
    }
    */
}