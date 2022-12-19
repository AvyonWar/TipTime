package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    //calcolo mancia martendo del numero inserito dall'utente
    private fun calculateTip() {
        // è neccessari 'toStirng()' perchè il 'text' attributo di an 'EditText' è aneditable;
        // perché rappresenta il testo che può essere modificato
        val stringInTextField = binding.costOfService.text.toString()
        // solo con il 'toString()' il 'toDouble()' funziona --> perchè deve essere chiamato su un file 'String'
        val cost =
            stringInTextField.toDoubleOrNull()  // <-- prende in considerazione anche il campo vuoto

        if (cost == null) {  // <-- se il campo è vuoto nn restituisce nulla (in questo caso restituisce un TOAST)
            //cancellare il precedente calcolo
            binding.tipResult.text = ""  //<-- in alternativa del TOAST - inserire qui l'avviso
            //avviso di che tipo di errore è
            return Toast.makeText(this, "enter a valid value", Toast.LENGTH_SHORT).show()
        }
        // ottenere la percentuale > prendere in esame i RadioGrup > definire le azioni per ogni caso presente nel RadioGroup
        /**
         * altro metodo più sintetico - suggerito da Android
         * -->
         * when(binding.tipOption.checkedRadioButtonId){
        R.id.option_twenty_percent -> 0.20
        R.id.option_eighteen_percent -> 0.18
        else -> 0.15
         */
        val selectedId = binding.tipOption.checkedRadioButtonId
        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        //calcolo mancia
        var tip = cost * tipPercentage
        //swich 'on' o 'off'?
        val roundUp = binding.roundUpSwitch.isChecked
        //se 'roudUp è vera -> se 'Swich' è su ON > allora esegui 'ceil()' -> funzione per
        // ARROTONDARE in ECCESSO una data cifra fornita al sistema
        if (roundUp) {
            tip = ceil(tip)
        }
        //visualizzare la mancia
        val formattedTip = NumberFormat.getNumberInstance().format(tip)
        binding.tipResult.text = getString(
            R.string.tip_amount,
            formattedTip
        ) // --> in string.xml ---> <string name="tip_amount">Tip Amount: %s</string>
    }
}

