package com.uas.aplikasiresepmakanan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.uas.aplikasiresepmakanan.R
import com.uas.aplikasiresepmakanan.model.Resep

class ResepAdapter(
    private val context: Context,
    private val list: MutableList<Resep>,
    private val onEdit: (Resep) -> Unit,
    private val onDelete: (Resep) -> Unit
) : RecyclerView.Adapter<ResepAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvDeskripsi = view.findViewById<TextView>(R.id.tvDeskripsi)
        val tvWaktu = view.findViewById<TextView>(R.id.tvWaktu)
        val tvPorsi = view.findViewById<TextView>(R.id.tvPorsi)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        val btnHapus = view.findViewById<Button>(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_resep, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resep = list[position]
        holder.tvNama.text = resep.nama
        holder.tvDeskripsi.text = resep.deskripsi
        holder.tvWaktu.text = "Waktu: ${resep.waktuMasak} menit"
        holder.tvPorsi.text = "Porsi: ${resep.porsi}"

        holder.btnEdit.setOnClickListener { onEdit(resep) }
        holder.btnHapus.setOnClickListener { onDelete(resep) }
    }
}
