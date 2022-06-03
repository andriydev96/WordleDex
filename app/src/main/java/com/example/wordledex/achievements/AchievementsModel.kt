package com.example.wordledex.achievements

import com.example.wordledex.R
import com.example.wordledex.database.PlayerData

class AchievementsModel {
    val achievementList = arrayListOf(
        Achievement("ROUTE 1","Catch your first pokémon. Your journey starts here!", R.drawable.route_1),
        Achievement("MISSION FAILED","Fail catching at least 36 pokémon. We'll get 'em next time.", R.drawable.mission_failed),
        Achievement("ELITE TRAINER","Catch at least 200 different pokémon species.", R.drawable.elite_trainer),
        Achievement("POKÉMON MASTER","Catch at least 400 unique pokémon species.", R.drawable.pokemon_master),
        Achievement("TRUE POKÉMON MASTER", "Gotta catch'em all!", R.drawable.true_pokemon_master),
        Achievement("WALKING POKÉDEX", "Perform 250 perfect guesses or more.", R.drawable.walking_pokedex),
        Achievement("PROFESSOR OAK", "Achieve 500 perfect guesses.", R.drawable.professor_oak),
        Achievement("SHINING STAR", "Catch your first shiny pokémon.", R.drawable.shining_star),
        Achievement("SHINE BRIGHT LIKE A DIAMOND", "Collect at least 25 unique shiny pokémon.", R.drawable.shine_bright_like_a_diamond),
        Achievement("RAINBOW ROAD", "Get at least 100 different shiny pokémon.", R.drawable.rainbow_road),
        Achievement("POKÉMON HOARDER", "Catch any 750 pokémon.", R.drawable.pokemon_hoarder),
        Achievement("TEAM ROCKET", "Capture 1500 pokémon across all species.", R.drawable.team_rocket),
        Achievement("POKÉMON RANGER", "Face 1000 pokémon or more.", R.drawable.pokemon_ranger),
        Achievement("VICTORY ROAD", "Encounter at least 2500 pokémon.", R.drawable.victory_road)
    )

    //Returns a list of achievements and their status
    fun getAchievementList(player: PlayerData) : ArrayList<Achievement>{
        for (i in achievementList.indices){
            when (i){
                0 -> if (player.gamesWon >= 1) achievementList[i].got = true //"ROUTE 1"
                1 -> if (player.gamesLost >= 36) achievementList[i].got = true //"MISSION FAILED"
                2 -> if (player.dexEntries >= 200) achievementList[i].got = true //"ELITE TRAINER"
                3 -> if (player.dexEntries >= 400) achievementList[i].got = true //"POKÉMON MASTER"
                4 -> if (player.dexEntries >= 898) achievementList[i].got = true //"TRUE POKÉMON MASTER"
                5 -> if (player.perfectWins >= 250) achievementList[i].got = true //"WALKING POKÉDEX"
                6 -> if (player.perfectWins >= 500) achievementList[i].got = true //"PROFESSOR OAK"
                7 -> if (player.shinyDexEntries >= 1) achievementList[i].got = true //"SHINING STAR"
                8 -> if (player.shinyDexEntries >= 25) achievementList[i].got = true //"SHINE BRIGHT LIKE A DIAMOND"
                9 -> if (player.shinyDexEntries >= 100) achievementList[i].got = true //"RAINBOW ROAD"
                10 -> if (player.gamesWon >= 750) achievementList[i].got = true //"POKÉMON HOARDER"
                11 -> if (player.gamesWon >= 1500) achievementList[i].got = true //"TEAM ROCKET"
                12 -> if (player.gamesPlayed >= 1000) achievementList[i].got = true //"POKÉMON RANGER"
                13 -> if (player.gamesPlayed >= 2500) achievementList[i].got = true //"VICTORY ROAD"
            }
        }
        return achievementList
    }
}