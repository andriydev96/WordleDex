package com.example.wordledex

import android.graphics.Bitmap

data class Pokemon(
        var dex: Int = 0,
        var name: String = "???",
        var height: Float = 0F,
        var weight: Float = 0F,
        var primaryType: String = "NONE",
        var secondaryType: String = "NONE",
        var captureRate: Int = 0,
        var description: String = "???",
        var spriteNormal: String? = null,
        var spriteShiny: String? = null,
        var artNormal: String? = null,
        var artShiny: String? = null,
        var icon: String? = null
        ){
        val generation : Int
            get() {
                return if (dex <= 151) 1
                else if (dex <= 251) 2
                else if (dex <= 386) 3
                else if (dex <= 493) 4
                else if (dex <= 649) 5
                else if (dex <= 721) 6
                else if (dex <= 809) 7
                else 8
            }
}