package com.example.szapps.Note

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope // IMPORT BARU UNTUK COROUTINE
import com.example.szapps.Data.AppDatabase
import com.example.szapps.Data.entity.NoteEntity
import com.example.szapps.databinding.ActivityNoteFormBinding
import kotlinx.coroutines.launch // IMPORT BARU UNTUK LAUNCH

class NoteFormActivity : AppCompatActivity() {

    // 1. Deklarasi variabel View Binding untuk menggantikan setContentView konvensional
    private lateinit var binding: ActivityNoteFormBinding

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aktifkan fitur layar penuh / edge-to-edge bawaan Android Studio terbaru
        enableEdgeToEdge()

        // 2. Inisialisasi View Binding secara sah
        binding = ActivityNoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Menghubungkan fungsi WindowInsets dengan root layout binding agar tampilan tidak tertutup Notch/Status Bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. Konfigurasi Toolbar Atas (Memasang judul dan tombol panah kembali)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Tambah Notes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon?.setTint(
            getColor(android.R.color.white)
        )
        supportActionBar?.setDisplayShowHomeEnabled(true)

        /** DI SINI TEMPAT NATORO INI JAN! (Inisialisasi DB) **/
        db = AppDatabase.getInstance(this)

        // 5. Logika Interaksi Tombol Save yang sudah digabung dengan fungsi Database asli
        binding.btnSaveNote.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            if (title.isNotBlank() && content.isNotBlank()) {
                /** Penggunaan Coroutine dalam melakukan insert data **/
                lifecycleScope.launch {
                    // Membuat objek catatan baru berdasarkan model NoteEntity
                    // Catatan: Jika kata 'NoteEntity' merah, arahkan kursor ke teksnya lalu tekan Alt + Enter untuk auto-import
                    val note = NoteEntity(
                        title = title,
                        content = content,
                        createdAt = System.currentTimeMillis()
                    )

                    // Menyimpan data ke database lokal melalui DAO
                    db.noteDao().insert(note)

                    Toast.makeText(this@NoteFormActivity, "Catatan '$title' Berhasil Tersimpan!", Toast.LENGTH_SHORT).show()
                    finish() // Menutup halaman form dan kembali ke list catatan
                }
            } else {
                Toast.makeText(this, "Isi semua kolom, Jan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 6. Fungsi override agar tombol panah kembali (<-) di Toolbar atas berfungsi secara fisik saat disentuh
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}