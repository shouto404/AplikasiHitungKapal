package com.example.kapal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHitungKapal = findViewById<Button>(R.id.btnHitungKapal)

        btnHitungKapal.setOnClickListener {
            val intent = Intent(this, KapalActivity::class.java)
            startActivity(intent)
        }
    }
}