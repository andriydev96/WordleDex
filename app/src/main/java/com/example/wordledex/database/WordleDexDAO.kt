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
    fun insertPokemonData(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayerData(playerData : PlayerData)

    @Query("SELECT * FROM pokemon ORDER BY dex")
    fun loadPokemonData() : List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE caught = 1 ORDER BY dex")
    fun loadOwnedPokemonData() : List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE caught = 0 ORDER BY dex")
    fun loadMissingPokemonData() : List<Pokemon>

    @Query("SELECT * FROM playerdata ORDER BY playerId")
    fun loadPlayerData() : PlayerData
}