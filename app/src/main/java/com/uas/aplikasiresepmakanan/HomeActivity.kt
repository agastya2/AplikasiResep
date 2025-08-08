package com.uas.aplikasiresepmakanan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uas.aplikasiresepmakanan.adapter.ResepAdapter
import com.uas.aplikasiresepmakanan.model.Resep

class HomeActivity : AppCompatActivity() {

    private lateinit var db: ResepDatabaseHelper
    private lateinit var adapter: ResepAdapter
    private lateinit var resepList: MutableList<Resep>

    private val editResepLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        refreshData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)

        val welcomeText = findViewById<TextView>(R.id.textWelcome)
        val logoutBtn = findViewById<Button>(R.id.btnLogout)
        val tambahBtn = findViewById<Button>(R.id.btnTambah)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerResep)

        welcomeText.text = "Selamat datang, $username!"

        db = ResepDatabaseHelper(this)
        resepList = db.getAllResep().toMutableList()

        adapter = ResepAdapter(this, resepList,
            onEdit = { resep -> editResep(resep) },
            onDelete = { resep -> deleteResep(resep) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        tambahBtn.setOnClickListener {
            val newResep = Resep(
                nama = "",
                deskripsi = "Deskripsi resep",
                waktuMasak = 0,
                porsi = 1,
                foto = ""
            )
            db.addResep(newResep)
            refreshData()
        }

        logoutBtn.setOnClickListener {
            sharedPref.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun refreshData() {
        resepList.clear()
        resepList.addAll(db.getAllResep())
        adapter.notifyDataSetChanged()
    }

    private fun deleteResep(resep: Resep) {
        db.deleteResep(resep.id)
        refreshData()
    }

    private fun editResep(resep: Resep) {
        val intent = Intent(this, EditResepActivity::class.java)
        intent.putExtra("id", resep.id)
        editResepLauncher.launch(intent)
    }
}
