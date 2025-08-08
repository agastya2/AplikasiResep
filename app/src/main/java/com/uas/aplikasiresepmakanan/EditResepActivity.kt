package com.uas.aplikasiresepmakanan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.uas.aplikasiresepmakanan.model.Resep

class EditResepActivity : AppCompatActivity() {

    private lateinit var db: ResepDatabaseHelper
    private var resepId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_resep)

        val edtNama = findViewById<EditText>(R.id.editNama)
        val edtDeskripsi = findViewById<EditText>(R.id.editDeskripsi)
        val edtWaktuMasak = findViewById<EditText>(R.id.editWaktuMasak)
        val edtPorsi = findViewById<EditText>(R.id.editPorsi)
        val edtFoto = findViewById<EditText>(R.id.editFoto)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanEdit)

        db = ResepDatabaseHelper(this)

        val intent = intent
        resepId = intent.getIntExtra("id", -1)

        if (resepId != -1) {
            val resep = db.getResepById(resepId)
            if (resep != null) {
                edtNama.setText(resep.nama)
                edtDeskripsi.setText(resep.deskripsi)
                edtWaktuMasak.setText(resep.waktuMasak.toString())
                edtPorsi.setText(resep.porsi.toString())
                edtFoto.setText(resep.foto)
            }
        }

        btnSimpan.setOnClickListener {
            val updatedResep = Resep(
                id = resepId,
                nama = edtNama.text.toString(),
                deskripsi = edtDeskripsi.text.toString(),
                waktuMasak = edtWaktuMasak.text.toString().toInt(),
                porsi = edtPorsi.text.toString().toInt(),
                foto = edtFoto.text.toString()
            )
            db.updateResep(updatedResep)
            finish()
        }
    }
}
