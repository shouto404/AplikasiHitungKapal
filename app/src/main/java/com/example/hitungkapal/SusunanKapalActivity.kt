package com.example.kapal

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.ContextCompat

class SusunanKapalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_susunan_kapal)

        val nama = intent.getStringExtra("NAMA_KAPAL") ?: "-"
        val tanggal = intent.getStringExtra("TANGGAL") ?: "-"

        val motor = intent.getIntExtra("MOTOR", 0)
        val mobil = intent.getIntExtra("MOBIL", 0)
        val truk = intent.getIntExtra("TRUK", 0)

        val hMobil = intent.getIntExtra("H_MOBIL", 0)
        val hTruk = intent.getIntExtra("H_TRUK", 0)

        val table = findViewById<TableLayout>(R.id.tableData)

        val totalHarga =
                    mobil * hMobil +
                    truk * hTruk

        val grid = findViewById<GridLayout>(R.id.gridKapal)


        table.addView(
            buatRow(
                "NAMA KAPAL",
                "TANGGAL",
                "MOBIL",
                "MOTOR",
                "TRUK",
                "TOTAL HARGA",
                isHeader = true
            )
        )

        table.addView(
            buatRow(
                nama,
                tanggal,
                mobil.toString(),
                motor.toString(),
                truk.toString(),
                "Rp $totalHarga"
            )
        )

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

    private fun buatRow(vararg data: String, isHeader: Boolean = false): TableRow {
        val row = TableRow(this)

        data.forEach {
            val tv = TextView(this)
            tv.text = it
            tv.setPadding(16, 12, 16, 12)
            tv.textSize = if (isHeader) 13f else 12f
            tv.setTextColor(if (isHeader) Color.WHITE else Color.parseColor("#1E293B"))
            tv.setTypeface(null, if (isHeader) Typeface.BOLD else Typeface.NORMAL)
            tv.background = ContextCompat.getDrawable(
                this,
                if (isHeader) R.drawable.bg_table_header else R.drawable.bg_table_cell
            )
            row.addView(tv)
        }
        return row
    }

}