package com.example.wordledex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pokemon::class, PlayerData::class], version = 1)
abstract class WordleDexDB : RoomDatabase() {
    abstract fun wordleDexDao() : WordleDexDAO
}

class WordleDexDatabase private constructor(context: Context){
    companion object: WordleDexDatabase.SingletonHolder<WordleDexDatabase, Context>(::WordleDexDatabase)

    open class SingletonHolder<out T, in A>(private val constructor: (A) -> T) {
        @Volatile
        private var instance: T? = null
        fun getInstance(arg: A): T =
                instance ?: synchronized(this) {
                    instance ?: constructor(arg).also { instance = it}
                }
    }

    val database = Room.databaseBuilder(
            context,
            WordleDexDB::class.java,
            "WordleDexDB"
    ).build()
}