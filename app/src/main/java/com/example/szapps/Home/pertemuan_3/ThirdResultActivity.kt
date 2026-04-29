package com.example.szapps.Home.pertemuan_3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.szapps.R

class ThirdResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_result)

        val nomor = intent.getStringExtra("nomor")

        val txtNomor = findViewById<TextView>(R.id.txtNomor)
        val btnKembali = findViewById<Button>(R.id.btnKembali)

        txtNomor.text = nomor

        btnKembali.setOnClickListener {
            finish()
        }
    }
}