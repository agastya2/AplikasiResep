package com.uas.aplikasiresepmakanan.model

data class Resep(
    var id: Int = 0,
    var nama: String,
    var deskripsi: String,
    var waktuMasak: Int,
    var porsi: Int,
    var foto: String
)
