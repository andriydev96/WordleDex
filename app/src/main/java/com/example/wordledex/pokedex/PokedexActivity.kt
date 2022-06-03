package com.example.wordledex.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordledex.R
import com.example.wordledex.database.Pokemon
import com.example.wordledex.pokedexEntry.PokedexEntryActivity

class PokedexActivity : AppCompatActivity() {
    lateinit var presenter: PokedexPresenter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)
        supportActionBar!!.hide()

        presenter = PokedexPresenter(this, PokedexModel(this))
        recyclerView = findViewById(R.id.recyclerViewPokedex)
    }

    //RecyclerView function
    fun displayPokedexEntries(ownedPokemonList : ArrayList<Pokemon>){
        if (recyclerView.adapter == null) {
            recyclerView.also {
                val adapter = PokedexAdapter(ownedPokemonList)
                it.adapter = adapter
                it.layoutManager = GridLayoutManager(this, 6)
                adapter.setOnItemClickListener(object: PokedexAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        launchDexEntryActivity(ownedPokemonList[position])
                    }
                })
            }
        } else
            recyclerView.adapter?.notifyDataSetChanged()
    }

    //Launches the selected pokédex entry activity
    fun launchDexEntryActivity(pokemon: Pokemon){
        val intent = Intent(this, PokedexEntryActivity::class.java).also {
            it.putExtra(POKEMON_DATA, pokemon)
        }
        startActivity(intent)
    }

    companion object{
        private const val POKEMON_DATA = "POKEMON_DATA"
    }
}