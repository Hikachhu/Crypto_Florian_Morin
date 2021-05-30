package com.example.crypto_florian_morin.bdd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

//room pour
class MySQLiteOpenHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        val creation = ("create table Questions("
                + "ID varchar(100) not null,"
                + "Name varchar(100) primary key ,"
                + "Symbol varchar(100) not null);")
        db.execSQL(creation)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}