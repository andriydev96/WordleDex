package com.example.wordledex.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordledex.R
import com.example.wordledex.database.Pokemon

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

    fun displayPokedexEntries(ownnedPokemonList : ArrayList<Pokemon>){
        if (recyclerView.adapter == null) {
            recyclerView.also {
                val adapter = PokedexAdapter(ownnedPokemonList)
                it.adapter = adapter
                it.layoutManager = GridLayoutManager(this, 6)
                /*adapter.setOnItemClickListener(object: PokedexAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        presenter.cocktailSelect(position)
                    }
                })*/
            }
        } else
            recyclerView.adapter?.notifyDataSetChanged()
    }
}