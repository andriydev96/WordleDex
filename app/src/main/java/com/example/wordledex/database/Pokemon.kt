package com.example.wordledex.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
        @PrimaryKey
        var dex: Int = 0,
        var name: String? = "???",
        var height: Float = 0F,
        var weight: Float = 0F,
        var primaryType: String? = "NONE",
        var secondaryType: String? = "NONE",

        var captureRate: Int = 0,
        var description: String? = "???",

        var spriteNormalURL: String? = "???",
        var spriteShinyURL: String? = "???",
        var artNormalURL: String? = "???",
        var artShinyURL: String? = "???",
        var iconURL: String? = "???",

        var caught: Boolean = false,
        var shinyCaught:  Boolean = false

        /*var spriteNormal: Bitmap? = null,
        var spriteShiny: Bitmap? = null,
        var artNormal: Bitmap? = null,
        var artShiny: Bitmap? = null,
        var icon: Bitmap? = null*/

        ) : Parcelable {
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

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dex)
        parcel.writeString(name)
        parcel.writeFloat(height)
        parcel.writeFloat(weight)
        parcel.writeString(primaryType)
        parcel.writeString(secondaryType)
        parcel.writeInt(captureRate)
        parcel.writeString(description)
        parcel.writeString(spriteNormalURL)
        parcel.writeString(spriteShinyURL)
        parcel.writeString(artNormalURL)
        parcel.writeString(artShinyURL)
        parcel.writeString(iconURL)
        parcel.writeByte(if (caught) 1 else 0)
        parcel.writeByte(if (shinyCaught) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}