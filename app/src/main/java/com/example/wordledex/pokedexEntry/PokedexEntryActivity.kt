package com.example.wordledex.pokedexEntry

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.wordledex.R
import com.example.wordledex.database.Pokemon
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PokedexEntryActivity : AppCompatActivity() {
    lateinit var pokemon: Pokemon
    lateinit var image: ImageView
    lateinit var imageShiny: ImageView
    lateinit var type1: ImageView
    lateinit var type2: ImageView
    lateinit var title: TextView
    lateinit var height: TextView
    lateinit var weight: TextView
    lateinit var region: TextView
    lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex_entry)
        supportActionBar!!.hide()

        pokemon = intent.getParcelableExtra<Pokemon>(POKEMON_DATA)!!

        image = findViewById(R.id.imageViewDexEntry)
        imageShiny = findViewById(R.id.imageViewDexEntry2)
        type1 = findViewById(R.id.imageViewType1DexEntry)
        type2 = findViewById(R.id.imageViewType2DexEntry)
        title = findViewById(R.id.textViewTitleDexEntry)
        height = findViewById(R.id.textViewHeightDexEntry)
        weight = findViewById(R.id.textViewWeightDexEntry)
        region = findViewById(R.id.textViewRegionDexEntry)
        description = findViewById(R.id.textViewDescriptionDexEntry)

        setDexEntryData()
    }

    companion object{
        private const val POKEMON_DATA = "POKEMON_DATA"
    }

    fun setDexEntryData(){
        Picasso.get().load(pokemon.artNormalURL).into(image, object: Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception?) {
                image.setImageResource(R.drawable.missing)
                toast("Error: couldn't download pokémon image from the internet. Check your connection.")
            }
        })
        Picasso.get().load(pokemon.artShinyURL).into(imageShiny, object: Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception?) {
                imageShiny.setImageResource(R.drawable.missing)
                toast("Error: couldn't download pokémon image from the internet. Check your connection.")
            }
        })
        if (pokemon.shinyCaught) imageShiny.setColorFilter(Color.parseColor("#00000000"))
        type1.setImageResource(getTypeImageId(pokemon.primaryType!!))
        type2.setImageResource(getTypeImageId(pokemon.secondaryType!!))
        title.text = "#${pokemon.dex} - ${pokemon.name}"
        height.text = "${pokemon.height} m"
        weight.text = "${pokemon.weight} kg"
        description.text = pokemon.description
        region.text = when (pokemon.generation){
            1 -> "Kanto"
            2 -> "Johto"
            3 -> "Hoenn"
            4 -> "Sinnoh"
            5 -> "Unova"
            6 -> "Kalos"
            7 -> "Alola"
            8 -> "Galar"
            else -> "Unknown"
        }
    }

    private fun getTypeImageId(pokemonType: String): Int {
        return when (pokemonType) {
            "BUG" -> R.drawable.bug
            "DARK" -> R.drawable.dark
            "DRAGON" -> R.drawable.dragon
            "ELECTRIC" -> R.drawable.electric
            "FAIRY" -> R.drawable.fairy
            "FIGHTING" -> R.drawable.fighting
            "FIRE" -> R.drawable.fire
            "FLYING" -> R.drawable.flying
            "GHOST" -> R.drawable.ghost
            "GRASS" -> R.drawable.grass
            "GROUND" -> R.drawable.ground
            "ICE" -> R.drawable.ice
            "NORMAL" -> R.drawable.normal
            "POISON" -> R.drawable.poison
            "PSYCHIC" -> R.drawable.psychic
            "ROCK" -> R.drawable.rock
            "STEEL" -> R.drawable.steel
            "WATER" -> R.drawable.water
            else -> R.drawable.none
        }
    }

    fun toast(text: String){
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
        toast.show()
    }
}