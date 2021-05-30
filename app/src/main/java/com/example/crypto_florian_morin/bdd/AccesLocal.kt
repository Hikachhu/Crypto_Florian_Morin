package com.example.crypto_florian_morin.bdd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class AccesLocal(context: Context?) {
    private val nomBase = "bdCryptoMonnaie.sqlite"
    private val versionBase = 1
    private var accesBD: MySQLiteOpenHelper = MySQLiteOpenHelper(context, nomBase, null, versionBase)
    private lateinit var bd: SQLiteDatabase
    fun ajout(ajouter: CryptoMonnaie) {
        bd = accesBD.writableDatabase
        val req = "insert into Questions(Id,Name,Symbol) values"+ "(\"" + ajouter.Name + "\",\"" + ajouter.ID + "\", \"" + ajouter.Symbol + "\");"
        bd.execSQL(req)
    }

    fun getSize(CryptoAChercher: String): Int {
        Log.e("CryptoAChercher", CryptoAChercher)
        val countQuery = "SELECT * FROM Questions WHERE Name like '%$CryptoAChercher%'"
        bd = accesBD.readableDatabase
        val cursor = bd.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun deleteBdd() {
        val req = "delete from Questions"
        bd = accesBD.readableDatabase
        bd.execSQL(req)
    }

    fun getCryptoMonnaie(CryptoAChercher: String, number: Int): CryptoMonnaie? {
        bd = accesBD.readableDatabase
        var question: CryptoMonnaie? = null
        val req = "select * from Questions where Name like '%$CryptoAChercher%'"
        val courser = bd.rawQuery(req, null)
        courser.move(number)
        if (!courser.isAfterLast) {
            val id = courser.getString(0)
            val name = courser.getString(1)
            val symbol = courser.getString(2)
            question = CryptoMonnaie(id, name, symbol)
        }
        courser.close()
        return question
    }

}