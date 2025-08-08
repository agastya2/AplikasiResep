package com.uas.aplikasiresepmakanan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uas.aplikasiresepmakanan.ResepDatabaseHelper
import com.uas.aplikasiresepmakanan.model.Resep

class TambahResepActivity : AppCompatActivity() {

    private lateinit var db: ResepDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_resep)

        db = ResepDatabaseHelper(this)

        val inputNama = findViewById<EditText>(R.id.inputNama)
        val inputDeskripsi = findViewById<EditText>(R.id.inputDeskripsi)
        val inputWaktu = findViewById<EditText>(R.id.inputWaktu)
        val inputPorsi = findViewById<EditText>(R.id.inputPorsi)
        val inputFoto = findViewById<EditText>(R.id.inputFoto)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val nama = inputNama.text.toString()
            val deskripsi = inputDeskripsi.text.toString()
            val waktuMasak = inputWaktu.text.toString().toIntOrNull() ?: 0
            val porsi = inputPorsi.text.toString().toIntOrNull() ?: 0
            val foto = inputFoto.text.toString()

            if (nama.isBlank() || deskripsi.isBlank()) {
                Toast.makeText(this, "Nama dan deskripsi wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resep = Resep(
                nama = nama,
                deskripsi = deskripsi,
                waktuMasak = waktuMasak,
                porsi = porsi,
                foto = foto
            )

            val result = db.insertResep(resep)
            if (result > 0) {
                Toast.makeText(this, "Resep berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal menyimpan resep", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
