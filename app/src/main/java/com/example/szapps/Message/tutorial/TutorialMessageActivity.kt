package com.example.szapps.Message.tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.szapps.databinding.ActivityTutorialMessageBinding

class TutorialMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityTutorialMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Set Toolbar sebagai Action Bar
        setSupportActionBar(binding.toolbarTutorial)

        // 2. Aktifkan tombol back resmi
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // 3. Aksi tombol back klik
        binding.toolbarTutorial.setNavigationOnClickListener {
            finish()
        }

        // --- PERBAIKAN DI SINI ---
        // Atur padding ke root layout (binding.main) agar status bar sistem
        // berada DI ATAS Toolbar dengan rapi, tidak menabrak teks.
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Berikan padding atas ke seluruh layar agar Toolbar turun ke posisi normal yang aman
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        // Setup ViewPager dengan adapter
        val fragmentsList = listOf(Tutorial1Fragment(), Tutorial2Fragment(), Tutorial3Fragment())
        val adapter = TutorialFragmentAdapter(this, fragmentsList)
        binding.tutorialMessageViewPager.adapter = adapter

        // Hubungkan WormDotsIndicator ke ViewPager2
        binding.wormDotsIndicator.attachTo(binding.tutorialMessageViewPager)
    }
}