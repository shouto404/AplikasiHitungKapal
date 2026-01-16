package com.example.kapal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class KapalActivity : AppCompatActivity() {

    private var kapasitas = 100
    private var slotMotor = 1
    private var slotMobil = 3
    private var slotTruk = 5

    private var motor = 0
    private var mobil = 0
    private var truk = 0

    private var namaKapal = ""
    private var tanggal = ""
    private var hargaMobil = 0
    private var hargaTruk = 0

    private lateinit var tvTotalKapasitas: TextView
    private lateinit var tvJumlah: TextView
    private lateinit var tvSisaKapasitas: TextView
    private lateinit var tvSisaUnit: TextView
    private lateinit var progressBarKapasitas: android.widget.ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kapal)

        // Inisialisasi ID Layout
        tvTotalKapasitas = findViewById(R.id.tvTotalKapasitas)
        tvJumlah = findViewById(R.id.tvJumlah)
        tvSisaKapasitas = findViewById(R.id.tvSisaKapasitas)
        tvSisaUnit = findViewById(R.id.tvSisaUnit)
        progressBarKapasitas = findViewById(R.id.progressBarKapasitas)

        val btnSetKapasitas = findViewById<Button>(R.id.btnSetKapasitas)
        val btnLihatSusunan = findViewById<Button>(R.id.btnLihatSusunan)
        val btnMotor = findViewById<Button>(R.id.btnMotor)
        val btnMobil = findViewById<Button>(R.id.btnMobil)
        val btnTruk = findViewById<Button>(R.id.btnTruk)

        btnSetKapasitas.setOnClickListener {
            showKonfigurasiPopup()
        }

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

            intent.putExtra("NAMA_KAPAL", namaKapal)
            intent.putExtra("TANGGAL", tanggal)

            intent.putExtra("MOTOR", motor)
            intent.putExtra("MOBIL", mobil)
            intent.putExtra("TRUK", truk)

            intent.putExtra("H_MOBIL", hargaMobil)
            intent.putExtra("H_TRUK", hargaTruk)
            startActivity(intent)

        }

        updateUI()
    }

    // Fungsi Popup Konfigurasi
    private fun showKonfigurasiPopup() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_konfigurasi, null)
        bottomSheetDialog.setContentView(view)

        val etKapasitas = view.findViewById<EditText>(R.id.etKapasitasPopup)
        val etSlotMobil = view.findViewById<EditText>(R.id.etSlotMobilPopup)
        val etSlotTruk = view.findViewById<EditText>(R.id.etSlotTrukPopup)
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpanKonfigurasi)
        val etNamaKapal = view.findViewById<EditText>(R.id.etNamaKapal)
        val etTanggal = view.findViewById<EditText>(R.id.etTanggal)
        val etHargaMobil = view.findViewById<EditText>(R.id.etHargaMobil)
        val etHargaTruk = view.findViewById<EditText>(R.id.etHargaTruk)
        namaKapal = etNamaKapal.text.toString()
        tanggal = etTanggal.text.toString()

        hargaMobil = etHargaMobil.text.toString().toIntOrNull() ?: 0
        hargaTruk = etHargaTruk.text.toString().toIntOrNull() ?: 0

        btnSimpan.setOnClickListener {
            val inputKaps = etKapasitas.text.toString().toIntOrNull() ?: kapasitas
            val etHargaMobil = view.findViewById<EditText>(R.id.etHargaMobil)
            val etHargaTruk = view.findViewById<EditText>(R.id.etHargaTruk)
            slotMobil = etSlotMobil.text.toString().toIntOrNull() ?: slotMobil
            slotTruk = etSlotTruk.text.toString().toIntOrNull() ?: slotTruk

            kapasitas = inputKaps
            motor = 0; mobil = 0; truk = 0

            tvTotalKapasitas.text = inputKaps.toString()

            namaKapal = etNamaKapal.text.toString()
            tanggal = etTanggal.text.toString()
            hargaMobil = etHargaMobil.text.toString().toIntOrNull() ?: 0
            hargaTruk = etHargaTruk.text.toString().toIntOrNull() ?: 0

            updateUI()

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    // Refresh Tampilan UI
    private fun updateUI() {
        tvJumlah.text = (motor + mobil + truk).toString()
        tvSisaKapasitas.text = kapasitas.toString()
        tvSisaUnit.text = "$kapasitas Unit Tersedia"
        progressBarKapasitas.progress = kapasitas
    }
}