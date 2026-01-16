package com.example.kapal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class KapalActivity : AppCompatActivity() {

    private var kapasitas = 0
    private var slotMotor = 1
    private var slotMobil = 3
    private var slotTruk = 5

    private var motor = 0
    private var mobil = 0
    private var truk = 0

    private lateinit var tvKapasitas: TextView
    private lateinit var tvJumlah: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kapal)

        tvKapasitas = findViewById(R.id.tvKapasitas)
        tvJumlah = findViewById(R.id.tvJumlah)

        //AMBIL VIEW INPUT
        val etKapasitas = findViewById<EditText>(R.id.etKapasitas)
        val etSlotMotor = findViewById<EditText>(R.id.etSlotMotor)
        val etSlotMobil = findViewById<EditText>(R.id.etSlotMobil)
        val etSlotTruk = findViewById<EditText>(R.id.etSlotTruk)
        val btnSetKapasitas = findViewById<Button>(R.id.btnSetKapasitas)
        val btnLihatSusunan = findViewById<Button>(R.id.btnLihatSusunan)


        //TOMBOL SET KAPASITAS
        btnSetKapasitas.setOnClickListener {
            kapasitas = etKapasitas.text.toString().toIntOrNull() ?: 0
            slotMotor = etSlotMotor.text.toString().toIntOrNull() ?: 1
            slotMobil = etSlotMobil.text.toString().toIntOrNull() ?: 3
            slotTruk = etSlotTruk.text.toString().toIntOrNull() ?: 5

            motor = 0
            mobil = 0
            truk = 0

            updateUI()
        }

        val btnMotor = findViewById<Button>(R.id.btnMotor)
        val btnMobil = findViewById<Button>(R.id.btnMobil)
        val btnTruk = findViewById<Button>(R.id.btnTruk)

        btnMotor.setOnClickListener {
            if (kapasitas >= slotMotor) {
                motor++
                kapasitas -= slotMotor
                updateUI()
            }
        }

        btnMobil.setOnClickListener {
            if (kapasitas >= slotMobil) {
                mobil++
                kapasitas -= slotMobil
                updateUI()
            }
        }
        btnTruk.setOnClickListener {
            if (kapasitas >= slotTruk) {
                truk++
                kapasitas -= slotTruk
                updateUI()
            }
        }
        btnLihatSusunan.setOnClickListener {
            val intent = Intent(this, SusunanKapalActivity::class.java)
            intent.putExtra("MOTOR", motor)
            intent.putExtra("MOBIL", mobil)
            intent.putExtra("TRUK", truk)
            startActivity(intent)
        }

    }

    private fun updateUI() {
        tvKapasitas.text = "Sisa Kapasitas: $kapasitas slot"
        tvJumlah.text = "Motor: $motor | Mobil: $mobil | Mobil: $truk"
    }
}