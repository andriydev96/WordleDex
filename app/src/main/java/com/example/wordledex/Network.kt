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
                    //Some name strings are not appropiate for the game, so we have to fix them...
                    when (pokemon.name) {
                        "MINIOR-RED-METEOR" -> pokemon.name = "MINIOR"
                        "WORMADAM-PLANT" -> pokemon.name = "WORMADAM"
                        "LYCANROC-MIDDAY" -> pokemon.name = "LYCANROC"
                        "GIRATINA-ALTERED" -> pokemon.name = "GIRATINA"
                        "MORPEKO-FULL-BELLY" -> pokemon.name = "MORPEKO"
                        "URSHIFU-SINGLE-STRIKE" -> pokemon.name = "URSHIFU"
                        "INDEEDEE-MALE" -> pokemon.name = "INDEEDEE"
                        "TOXTRICITY-AMPED" -> pokemon.name = "TOXTRICITY"
                        "MIMIKYU-DISGUISED" -> pokemon.name = "MIMIKYU"
                        "GOURGEIST-AVERAGE" -> pokemon.name = "GOURGEIST"
                        "PUMPKABOO-AVERAGE" -> pokemon.name = "PUMPKABOO"
                        "ORICORIO-BAILE" -> pokemon.name = "ORICORIO"
                        "WISHIWASHI-SOLO" -> pokemon.name = "WISHIWASHI"
                        "AEGISLASH-SHIELD" -> pokemon.name = "AEGISLASH"
                        "MEOWSTIC-MALE" -> pokemon.name = "MEOWSTIC"
                        "THUNDURUS-INCARNATE" -> pokemon.name = "THUNDURUS"
                        "TORNADUS-INCARNATE" -> pokemon.name = "TORNADUS"
                        "LANDORUS-INCARNATE" -> pokemon.name = "LANDORUS"
                        "KELDEO-ORDINARY" -> pokemon.name = "KELDEO"
                        "MELOETTA-ARIA" -> pokemon.name = "MELOETTA"
                        "DARMANITAN-STANDARD" -> pokemon.name = "DARMANITAN"
                        "SHAYMIN-LAND" -> pokemon.name = "SHAYMIN"
                        "DEOXYS-NORMAL" -> pokemon.name = "DEOXYS"
                        "BASCULIN-RED-STRIPED" -> pokemon.name = "BASCULIN"
                        "ZYGARDE-50" -> pokemon.name = "ZYGARDE"
                    }

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

                    if (pokemon.artNormalURL == "null") pokemon.artNormalURL = pokemon.spriteNormalURL
                    if (pokemon.artShinyURL == "null") pokemon.artShinyURL = pokemon.spriteShinyURL

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
}