package com.example.kapal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class KapalActivity : AppCompatActivity() {

    private var totalPendapatan = 0
    private var kapasitasMaksimal = 100
    private var kapasitasSisa = 100

    // Harga & Beban (Semuanya Dinamis)
    private var hargaMotor = 5000;  private var slotMotor = 1
    private var hargaMobil = 15000; private var slotMobil = 3
    private var hargaTruk = 50000;  private var slotTruk = 5

    private var motorCount = 0; private var mobilCount = 0; private var trukCount = 0
    private var isModeHapusAktif = false

    private lateinit var tvTotalPendapatan: TextView
    private lateinit var tvJumlah: TextView
    private lateinit var tvSisaKapasitas: TextView
    private lateinit var tvTotalKapasitas: TextView
    private lateinit var tvSisaUnit: TextView
    private lateinit var tableData: LinearLayout
    private lateinit var progressBarKapasitas: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kapal)

        // Inisialisasi ID
        tvTotalPendapatan = findViewById(R.id.tvTotalPendapatan)
        tvJumlah = findViewById(R.id.tvJumlah)
        tvSisaKapasitas = findViewById(R.id.tvSisaKapasitas)
        tvTotalKapasitas = findViewById(R.id.tvTotalKapasitas)
        tvSisaUnit = findViewById(R.id.tvSisaUnit)
        tableData = findViewById(R.id.tableData)
        progressBarKapasitas = findViewById(R.id.progressBarKapasitas)

        // Klik Tombol Kendaraan
        findViewById<Button>(R.id.btnMotor).setOnClickListener { tambahKendaraan("Motor", hargaMotor, slotMotor) }
        findViewById<Button>(R.id.btnMobil).setOnClickListener { tambahKendaraan("Mobil", hargaMobil, slotMobil) }
        findViewById<Button>(R.id.btnTruk).setOnClickListener { tambahKendaraan("Truk", hargaTruk, slotTruk) }

        findViewById<Button>(R.id.btnSetKapasitas).setOnClickListener { showKonfigurasiPopup() }

        findViewById<ImageButton>(R.id.btnModeEdit).setOnClickListener {
            isModeHapusAktif = !isModeHapusAktif
            updateVisibilityTombolHapus()
            Toast.makeText(this, "Mode Edit: $isModeHapusAktif", Toast.LENGTH_SHORT).show()
        }

        updateUI()
    }

    private fun showKonfigurasiPopup() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_konfigurasi, null)
        bottomSheetDialog.setContentView(view)

        view.findViewById<Button>(R.id.btnSimpanKonfigurasi).setOnClickListener {
            // Update Kapasitas Total
            kapasitasMaksimal = view.findViewById<EditText>(R.id.etKapasitasPopup).text.toString().toIntOrNull() ?: kapasitasMaksimal
            kapasitasSisa = kapasitasMaksimal

            // Update Harga & Beban dari Popup
            hargaMotor = view.findViewById<EditText>(R.id.etHargaMotorPopup).text.toString().toIntOrNull() ?: hargaMotor
            slotMotor = view.findViewById<EditText>(R.id.etSlotMotorPopup).text.toString().toIntOrNull() ?: slotMotor

            hargaMobil = view.findViewById<EditText>(R.id.etHargaMobilPopup).text.toString().toIntOrNull() ?: hargaMobil
            slotMobil = view.findViewById<EditText>(R.id.etSlotMobilPopup).text.toString().toIntOrNull() ?: slotMobil

            hargaTruk = view.findViewById<EditText>(R.id.etHargaTrukPopup).text.toString().toIntOrNull() ?: hargaTruk
            slotTruk = view.findViewById<EditText>(R.id.etSlotTrukPopup).text.toString().toIntOrNull() ?: slotTruk

            // Reset Dashboard
            motorCount = 0; mobilCount = 0; trukCount = 0
            tableData.removeAllViews()
            updateUI()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun tambahKendaraan(jenis: String, harga: Int, beban: Int) {
        if (kapasitasSisa >= beban) {
            when (jenis) {
                "Motor" -> motorCount++
                "Mobil" -> mobilCount++
                "Truk" -> trukCount++
            }
            kapasitasSisa -= beban

            val row = LayoutInflater.from(this).inflate(R.layout.item_kendaraan, tableData, false)
            row.findViewById<TextView>(R.id.tvJenis).text = jenis
            row.findViewById<TextView>(R.id.tvWaktu).text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            row.findViewById<TextView>(R.id.tvHarga).text = "Rp $harga"

            val btnHapus = row.findViewById<ImageButton>(R.id.btnHapus)
            btnHapus.visibility = if (isModeHapusAktif) View.VISIBLE else View.GONE
            btnHapus.setOnClickListener {
                tableData.removeView(row)
                when (jenis) {
                    "Motor" -> motorCount--
                    "Mobil" -> mobilCount--
                    "Truk" -> trukCount--
                }
                kapasitasSisa += beban
                updateUI()
            }
            tableData.addView(row, 0)
            updateUI()
        } else {
            Toast.makeText(this, "Kapasitas Penuh!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        totalPendapatan = (motorCount * hargaMotor) + (mobilCount * hargaMobil) + (trukCount * hargaTruk)
        tvTotalPendapatan.text = "Rp $totalPendapatan"
        tvJumlah.text = (motorCount + mobilCount + trukCount).toString()
        tvSisaKapasitas.text = kapasitasSisa.toString()
        tvTotalKapasitas.text = kapasitasMaksimal.toString()
        tvSisaUnit.text = "$kapasitasSisa Unit Sisa"
        progressBarKapasitas.max = kapasitasMaksimal
        progressBarKapasitas.progress = (kapasitasMaksimal - kapasitasSisa)
    }

    private fun updateVisibilityTombolHapus() {
        for (i in 0 until tableData.childCount) {
            val row = tableData.getChildAt(i)
            row.findViewById<ImageButton>(R.id.btnHapus).visibility = if (isModeHapusAktif) View.VISIBLE else View.GONE
        }
    }
}