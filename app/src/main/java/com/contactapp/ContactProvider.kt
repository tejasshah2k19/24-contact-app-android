package com.contactapp


import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.provider.BaseColumns

class ContactProvider : ContentProvider() {

    companion object {
        private const val CONTACTS = 1
        private const val CONTACT_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(ContactContract.AUTHORITY, "contacts", CONTACTS)
            addURI(ContactContract.AUTHORITY, "contacts/#", CONTACT_ID)
        }
    }

    private lateinit var dbHelper: ContactDbHelper

    override fun onCreate(): Boolean {
        dbHelper = ContactDbHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        return when (uriMatcher.match(uri)) {
            CONTACTS -> db.query(ContactContract.ContactEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            CONTACT_ID -> {
                val id = ContentUris.parseId(uri)
                db.query(ContactContract.ContactEntry.TABLE_NAME, projection, "${BaseColumns._ID}=?", arrayOf(id.toString()), null, null, sortOrder)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        return when (uriMatcher.match(uri)) {
            CONTACTS -> {
                val id = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values)
                context?.contentResolver?.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return when (uriMatcher.match(uri)) {
            CONTACTS -> db.delete(ContactContract.ContactEntry.TABLE_NAME, selection, selectionArgs)
            CONTACT_ID -> {
                val id = ContentUris.parseId(uri)
                db.delete(ContactContract.ContactEntry.TABLE_NAME, "${BaseColumns._ID}=?", arrayOf(id.toString()))
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return when (uriMatcher.match(uri)) {
            CONTACTS -> db.update(ContactContract.ContactEntry.TABLE_NAME, values, selection, selectionArgs)
            CONTACT_ID -> {
                val id = ContentUris.parseId(uri)
                db.update(ContactContract.ContactEntry.TABLE_NAME, values, "${BaseColumns._ID}=?", arrayOf(id.toString()))
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            CONTACTS -> "vnd.android.cursor.dir/vnd.${ContactContract.AUTHORITY}.contacts"
            CONTACT_ID -> "vnd.android.cursor.item/vnd.${ContactContract.AUTHORITY}.contacts"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    private class ContactDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_NAME = "contacts.db"
            private const val DATABASE_VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase) {
            val createTable = "CREATE TABLE ${ContactContract.ContactEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${ContactContract.ContactEntry.COLUMN_NAME_NAME} TEXT NOT NULL, " +
                    "${ContactContract.ContactEntry.COLUMN_NAME_TYPE} TEXT NOT NULL,"+
                    "${ContactContract.ContactEntry.COLUMN_NAME_CONTACT} TEXT NOT NULL)"
            db.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${ContactContract.ContactEntry.TABLE_NAME}")
            onCreate(db)
        }
    }
}
