package com.example.kapal

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SusunanKapalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_susunan_kapal)

        val motor = intent.getIntExtra("motor", 0)
        val mobil = intent.getIntExtra("mobil", 0)
        val truk = intent.getIntExtra("truk", 0)

        val grid = findViewById<GridLayout>(R.id.gridKapal)

        println("DEBUG MOTOR=$motor MOBIL=$mobil TRUK=$truk")

        tampilkanKendaraan(grid, motor, mobil, truk)
    }

    private fun tampilkanKendaraan(
        grid: GridLayout,
        motor: Int,
        mobil: Int,
        truk: Int
    ) {
        grid.removeAllViews()

        repeat(motor) {
            tambahIcon(grid, R.drawable.ic_motor)
        }

        repeat(mobil) {
            tambahIcon(grid, R.drawable.ic_mobil)
        }

        repeat(truk) {
            tambahIcon(grid, R.drawable.ic_truk)
        }
    }

    private fun tambahIcon(grid: GridLayout, drawable: Int) {
        val img = ImageView(this)
        img.setImageResource(drawable)

        img.layoutParams = GridLayout.LayoutParams().apply {
            width = 160
            height = 160
            setMargins(8, 8, 8, 8)
        }

        grid.addView(img)
    }

}