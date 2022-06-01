package com.example.wordledex.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.wordledex.R
import com.example.wordledex.database.PlayerData
import com.example.wordledex.database.Pokemon
import com.squareup.picasso.Picasso

class GameActivity : AppCompatActivity() {
    lateinit var pokemon: Pokemon
    lateinit var playerData: PlayerData

    lateinit var presenter: GamePresenter

    lateinit var silhouetteImageView: ImageView
    lateinit var imageViewType1 : ImageView
    lateinit var imageViewType2 : ImageView
    lateinit var buttonGuess: Button
    lateinit var buttonA: Button
    lateinit var buttonB: Button
    lateinit var buttonC: Button
    lateinit var buttonD: Button
    lateinit var buttonE: Button
    lateinit var buttonF: Button
    lateinit var buttonG: Button
    lateinit var buttonH: Button
    lateinit var buttonI: Button
    lateinit var buttonJ: Button
    lateinit var buttonK: Button
    lateinit var buttonL: Button
    lateinit var buttonM: Button
    lateinit var buttonN: Button
    lateinit var buttonO: Button
    lateinit var buttonP: Button
    lateinit var buttonQ: Button
    lateinit var buttonR: Button
    lateinit var buttonS: Button
    lateinit var buttonT: Button
    lateinit var buttonU: Button
    lateinit var buttonV: Button
    lateinit var buttonW: Button
    lateinit var buttonY: Button
    lateinit var buttonX: Button
    lateinit var buttonZ: Button
    lateinit var buttonDash: Button
    lateinit var buttonComa: Button
    lateinit var buttonDelete: Button
    lateinit var textWho : TextView
    lateinit var textViewGeneration : TextView
    lateinit var lifeBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_activitty)
        supportActionBar!!.hide()

        pokemon = intent.getParcelableExtra<Pokemon>(POKEMON_DATA)!!
        playerData = intent.getParcelableExtra<PlayerData>(PLAYER_DATA)!!

        presenter = GamePresenter(this, GameModel(this))
        silhouetteImageView = findViewById(R.id.silhouetteImageView)
        imageViewType1 = findViewById(R.id.imageViewType1)
        imageViewType2 = findViewById(R.id.imageViewType2)
        buttonGuess = findViewById(R.id.buttonGuess)
        buttonGuess.setOnClickListener { presenter.guessButtonPressed(pokemon) }
        buttonA = findViewById(R.id.buttonA)
        buttonA.setOnClickListener { presenter.keyPressed("A", pokemon) }
        buttonB = findViewById(R.id.buttonB)
        buttonB.setOnClickListener { presenter.keyPressed("B", pokemon) }
        buttonC = findViewById(R.id.buttonC)
        buttonC.setOnClickListener { presenter.keyPressed("C", pokemon) }
        buttonD = findViewById(R.id.buttonD)
        buttonD.setOnClickListener { presenter.keyPressed("D", pokemon) }
        buttonE = findViewById(R.id.buttonE)
        buttonE.setOnClickListener { presenter.keyPressed("E", pokemon) }
        buttonF = findViewById(R.id.buttonF)
        buttonF.setOnClickListener { presenter.keyPressed("F", pokemon) }
        buttonG = findViewById(R.id.buttonG)
        buttonG.setOnClickListener { presenter.keyPressed("G", pokemon) }
        buttonH = findViewById(R.id.buttonH)
        buttonH.setOnClickListener { presenter.keyPressed("H", pokemon) }
        buttonI = findViewById(R.id.buttonI)
        buttonI.setOnClickListener { presenter.keyPressed("I", pokemon) }
        buttonJ = findViewById(R.id.buttonJ)
        buttonJ.setOnClickListener { presenter.keyPressed("J", pokemon) }
        buttonK = findViewById(R.id.buttonK)
        buttonK.setOnClickListener { presenter.keyPressed("K", pokemon) }
        buttonL = findViewById(R.id.buttonL)
        buttonL.setOnClickListener { presenter.keyPressed("L", pokemon) }
        buttonM = findViewById(R.id.buttonM)
        buttonM.setOnClickListener { presenter.keyPressed("M", pokemon) }
        buttonN = findViewById(R.id.buttonN)
        buttonN.setOnClickListener { presenter.keyPressed("N", pokemon) }
        buttonO = findViewById(R.id.buttonO)
        buttonO.setOnClickListener { presenter.keyPressed("O", pokemon) }
        buttonP = findViewById(R.id.buttonP)
        buttonP.setOnClickListener { presenter.keyPressed("P", pokemon) }
        buttonQ = findViewById(R.id.buttonQ)
        buttonQ.setOnClickListener { presenter.keyPressed("Q", pokemon) }
        buttonR = findViewById(R.id.buttonR)
        buttonR.setOnClickListener { presenter.keyPressed("R", pokemon) }
        buttonS = findViewById(R.id.buttonS)
        buttonS.setOnClickListener { presenter.keyPressed("S", pokemon) }
        buttonT = findViewById(R.id.buttonT)
        buttonT.setOnClickListener { presenter.keyPressed("T", pokemon) }
        buttonU = findViewById(R.id.buttonU)
        buttonU.setOnClickListener { presenter.keyPressed("U", pokemon) }
        buttonV = findViewById(R.id.buttonV)
        buttonV.setOnClickListener { presenter.keyPressed("V", pokemon) }
        buttonW = findViewById(R.id.buttonW)
        buttonW.setOnClickListener { presenter.keyPressed("W", pokemon) }
        buttonY = findViewById(R.id.buttonY)
        buttonY.setOnClickListener { presenter.keyPressed("Y", pokemon) }
        buttonX = findViewById(R.id.buttonX)
        buttonX.setOnClickListener { presenter.keyPressed("X", pokemon) }
        buttonZ = findViewById(R.id.buttonZ)
        buttonZ.setOnClickListener { presenter.keyPressed("Z", pokemon) }
        buttonDash = findViewById(R.id.buttonDash)
        buttonDash.setOnClickListener { presenter.keyPressed("-", pokemon) }
        buttonComa = findViewById(R.id.buttonComa)
        buttonComa.setOnClickListener { presenter.keyPressed("'", pokemon) }
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener { presenter.keyPressed("DELETE", pokemon) }
        textWho = findViewById(R.id.textWho)
        textViewGeneration = findViewById(R.id.textViewGeneration)
        lifeBar = findViewById(R.id.lifeBar)

        Picasso.get().load(pokemon.artNormalURL).into(silhouetteImageView)
        clearButtonText()

        Log.d("APP-ACTION", "$pokemon")
    }

    fun clearButtonText(){
        var text = ""
        for (i in 1..pokemon.name!!.length){
            text += "_"
        }
        buttonGuess.text = text
    }

    fun setButtonText(text : String){
        buttonGuess.text = text
    }

    fun setWhoText(text : SpannableString){
        textWho.text = text
    }

    fun setLife(life : Int){
        lifeBar.progress = life
    }

    fun setGeneration(genString : String){
        textViewGeneration.text = genString
    }

    fun gameOver(life : Int, pokemon: Pokemon){
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.game_over_dialog, null)
        Picasso.get().load(pokemon.artNormalURL).into(view.findViewById<ImageView>(R.id.imageViewGO))

        if (life != 0){
            view.findViewById<TextView>(R.id.textViewTitleGO).text = "CONGRATULATIONS!"
            val descriptionGO = view.findViewById<TextView>(R.id.textViewDescriptionGO)
            when (life){
                5 -> descriptionGO.text = "Incredible! You have caught ${pokemon.name} on your first try!"
                4 -> descriptionGO.text = "You have caught ${pokemon.name} on your second try, that's amazing!"
                3 -> descriptionGO.text = "Not bad! It only took you three tries to catch ${pokemon.name}!"
                2 -> descriptionGO.text = "Well done, you have caught ${pokemon.name} on your fourth attempt!"
                1 -> descriptionGO.text = "That was close, but you were able to catch ${pokemon.name}!"
            }
        } else {
            view.findViewById<TextView>(R.id.textViewTitleGO).text = "OH NO..."
            view.findViewById<TextView>(R.id.textViewDescriptionGO).text = "The wild pokémon ran away... the answer was ${pokemon.name}"
            view.findViewById<TextView>(R.id.textViewDescription2GO).text = "Better luck next time!"
        }
        builder.setOnDismissListener { finish() }
        builder.setView(view)
        val dialog = builder.create()
        view.findViewById<Button>(R.id.buttonGO).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    companion object{
        private const val POKEMON_DATA = "POKEMON_DATA"
        private const val PLAYER_DATA = "PLAYER_DATA"
    }
}