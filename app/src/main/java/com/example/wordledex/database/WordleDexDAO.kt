package com.example.wordledex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordleDexDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonData(pokemonList: ArrayList<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayerData(playerData : PlayerData)

    @Query("SELECT * FROM pokemon ORDER BY dex")
    fun loadPokemonData() : List<Pokemon>

    @Query("SELECT * FROM playerdata ORDER BY playerId")
    fun loadPlayerData() : PlayerData
}