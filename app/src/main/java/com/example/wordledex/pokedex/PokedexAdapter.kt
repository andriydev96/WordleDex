package com.example.wordledex.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordledex.R
import com.example.wordledex.database.Pokemon
import com.squareup.picasso.Picasso

class PokedexAdapter(val ownedPokemonList : ArrayList<Pokemon>):RecyclerView.Adapter<PokedexAdapter.PokedexHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokedexHolder(layoutInflater.inflate(R.layout.item_pokedex_entry, parent, false))
    }

    override fun onBindViewHolder(holder: PokedexHolder, position: Int) {
        holder.render(ownedPokemonList[position])
    }

    override fun getItemCount(): Int = ownedPokemonList.size

    class PokedexHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun render(pokemon : Pokemon){
            view.findViewById<TextView>(R.id.textViewDex).text = "#${pokemon.dex.toString().padStart(3, '0')}"
            Picasso.get().load(pokemon.spriteNormalURL).into(view.findViewById<ImageView>(R.id.imageViewDex))
        }
    }
}