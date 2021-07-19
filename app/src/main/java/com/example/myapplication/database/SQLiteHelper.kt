package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.models.Database

class SQLiteHelper(context: Context,
                   factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addInfo(name: Database, latLng: Database) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name.userName)
        values.put(COLUMN_LATLNG, latLng.latLng)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "izhevskonline.db"
        const val TABLE_NAME = "name"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "username"
        const val COLUMN_LATLNG = "latlng"
    }
}