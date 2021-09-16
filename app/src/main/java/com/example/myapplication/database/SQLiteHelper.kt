package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.models.Database
import com.example.myapplication.models.DatabaseItem

class SQLiteHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_EXCURSION_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT," +
                COLUMN_LATLNG
                + " TEXT"
                + ")")
        db.execSQL(CREATE_EXCURSION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addInfo(name: Database) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name.name)
        values.put(COLUMN_LATLNG, name.latLng)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        //db.close()
    }

    fun addItem(name: DatabaseItem) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name.name)
        values.put(COLUMN_LATLNG, name.latLng)
        values.put(COLUMN_RATING, name.rating)
        values.put(COLUMN_IMAGE, name.image)
        values.put(COLUMN_NAMESCREEN, name.nameScreen)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_ITEM, null, values)
        //db.close()
    }

    fun getAllName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    fun getAllItem(name: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_ITEM WHERE $COLUMN_NAMESCREEN = '$name'", null)
    }
    fun execSQL(sql: String) {
        val db = this.readableDatabase
        db.execSQL(sql)
    }

    fun createItemBD() {
        val db = this.writableDatabase
        val CREATE_ITEM_TABLE = ("CREATE TABLE IF NOT EXISTS $TABLE_NAME_ITEM ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_LATLNG TEXT, $COLUMN_RATING TEXT, $COLUMN_IMAGE TEXT, $COLUMN_NAMESCREEN TEXT)")
        db.execSQL(CREATE_ITEM_TABLE)
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "izhevskonline.db"
        const val TABLE_NAME = "localExcursion"
        const val TABLE_NAME_ITEM = "localItem"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LATLNG = "latlng"
        const val COLUMN_RATING = "rating"
        const val COLUMN_IMAGE = "imageurl"
        const val COLUMN_NAMESCREEN = "nameScreen"
    }
}