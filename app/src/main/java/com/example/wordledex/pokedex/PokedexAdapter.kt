package com.example.wordledex.pokedex

import android.graphics.Color
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

    private lateinit var itemListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        itemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokedexHolder(layoutInflater.inflate(R.layout.item_pokedex_entry, parent, false), itemListener)
    }

    override fun onBindViewHolder(holder: PokedexHolder, position: Int) {
        holder.render(ownedPokemonList[position])
    }

    override fun getItemCount(): Int = ownedPokemonList.size

    class PokedexHolder(val view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view){
        fun render(pokemon : Pokemon){
            val numberTextView = view.findViewById<TextView>(R.id.textViewDex)
            numberTextView.text = "#${pokemon.dex.toString().padStart(3, '0')}"
            if (pokemon.shinyCaught) numberTextView.setTextColor(Color.parseColor("#FFD700"))
            Picasso.get().load(pokemon.spriteNormalURL).into(view.findViewById<ImageView>(R.id.imageViewDex))
        }
        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}