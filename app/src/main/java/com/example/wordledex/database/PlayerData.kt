package com.example.wordledex.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity
data class PlayerData(
        @PrimaryKey
        var playerId : Int = Random.nextInt(100000, 999999),

        //STATS
        var gamesPlayed : Int = 0,
        var gamesWon : Int = 0,
        var gamesLost : Int = 0
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(playerId)
                parcel.writeInt(gamesPlayed)
                parcel.writeInt(gamesWon)
                parcel.writeInt(gamesLost)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<PlayerData> {
                override fun createFromParcel(parcel: Parcel): PlayerData {
                        return PlayerData(parcel)
                }

                override fun newArray(size: Int): Array<PlayerData?> {
                        return arrayOfNulls(size)
                }
        }
}
