package com.uas.aplikasiresepmakanan

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.uas.aplikasiresepmakanan.model.Resep

class ResepDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "resep.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_RESEP = "resep"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_DESKRIPSI = "deskripsi"
        private const val COLUMN_WAKTU = "waktuMasak"
        private const val COLUMN_PORSI = "porsi"
        private const val COLUMN_FOTO = "foto"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_RESEP ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAMA TEXT, "
                + "$COLUMN_DESKRIPSI TEXT, "
                + "$COLUMN_WAKTU INTEGER, "
                + "$COLUMN_PORSI INTEGER, "
                + "$COLUMN_FOTO TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RESEP")
        onCreate(db)
    }

    fun addResep(resep: Resep): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, resep.nama)
            put(COLUMN_DESKRIPSI, resep.deskripsi)
            put(COLUMN_WAKTU, resep.waktuMasak)
            put(COLUMN_PORSI, resep.porsi)
            put(COLUMN_FOTO, resep.foto)
        }
        val id = db.insert(TABLE_RESEP, null, values)
        db.close()
        return id
    }

    fun getAllResep(): List<Resep> {
        val list = mutableListOf<Resep>()
        val query = "SELECT * FROM $TABLE_RESEP"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val resep = Resep(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)),
                    deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESKRIPSI)),
                    waktuMasak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WAKTU)),
                    porsi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PORSI)),
                    foto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOTO))
                )
                list.add(resep)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun updateResep(resep: Resep): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, resep.nama)
            put(COLUMN_DESKRIPSI, resep.deskripsi)
            put(COLUMN_WAKTU, resep.waktuMasak)
            put(COLUMN_PORSI, resep.porsi)
            put(COLUMN_FOTO, resep.foto)
        }

        val result = db.update(TABLE_RESEP, values, "$COLUMN_ID=?", arrayOf(resep.id.toString()))
        db.close()
        return result
    }

    fun deleteResep(id: Int): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_RESEP, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun insertResep(resep: Resep): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nama", resep.nama)
            put("deskripsi", resep.deskripsi)
            put("waktuMasak", resep.waktuMasak)
            put("porsi", resep.porsi)
            put("foto", resep.foto)
        }
        return db.insert("resep", null, values)
    }

    fun getResepById(id: Int): Resep? {
        val db = this.readableDatabase
        var resep: Resep? = null

        val cursor = db.query(
            TABLE_RESEP,
            arrayOf(COLUMN_ID, COLUMN_NAMA, COLUMN_DESKRIPSI, COLUMN_WAKTU, COLUMN_PORSI, COLUMN_FOTO),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            resep = Resep(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)),
                deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESKRIPSI)),
                waktuMasak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WAKTU)),
                porsi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PORSI)),
                foto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOTO))
            )
        }

        cursor.close()
        db.close()
        return resep
    }

}
