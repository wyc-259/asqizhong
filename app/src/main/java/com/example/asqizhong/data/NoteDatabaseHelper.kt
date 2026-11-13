package com.example.asqizhong.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT NOT NULL,
                $COL_CONTENT TEXT NOT NULL,
                $COL_CATEGORY TEXT NOT NULL,
                $COL_PINNED INTEGER NOT NULL DEFAULT 0,
                $COL_CREATED INTEGER NOT NULL,
                $COL_UPDATED INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun insert(title: String, content: String, category: String): Long {
        val now = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(COL_TITLE, title)
            put(COL_CONTENT, content)
            put(COL_CATEGORY, category)
            put(COL_PINNED, 0)
            put(COL_CREATED, now)
            put(COL_UPDATED, now)
        }
        return writableDatabase.insert(TABLE, null, values)
    }

    fun update(note: Note) {
        val values = ContentValues().apply {
            put(COL_TITLE, note.title)
            put(COL_CONTENT, note.content)
            put(COL_CATEGORY, note.category)
            put(COL_PINNED, if (note.pinned) 1 else 0)
            put(COL_UPDATED, System.currentTimeMillis())
        }
        writableDatabase.update(TABLE, values, "$COL_ID=?", arrayOf(note.id.toString()))
    }

    fun setPinned(id: Long, pinned: Boolean) {
        val values = ContentValues().apply {
            put(COL_PINNED, if (pinned) 1 else 0)
        }
        writableDatabase.update(TABLE, values, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun delete(id: Long) {
        writableDatabase.delete(TABLE, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun readAll(): List<Note> {
        val list = mutableListOf<Note>()
        readableDatabase.query(
            TABLE, null, null, null, null, null, "$COL_UPDATED DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) list.add(cursorToNote(cursor))
        }
        return list
    }

    fun readById(id: Long): Note? {
        readableDatabase.query(
            TABLE, null, "$COL_ID=?", arrayOf(id.toString()), null, null, null
        ).use { cursor ->
            return if (cursor.moveToFirst()) cursorToNote(cursor) else null
        }
    }

    private fun cursorToNote(cursor: Cursor): Note = Note(
        id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
        title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
        content = cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)),
        category = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
        pinned = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PINNED)) == 1,
        createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_CREATED)),
        updatedAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_UPDATED))
    )

    companion object {
        const val DB_NAME = "notepad.db"
        const val DB_VERSION = 1
        const val TABLE = "notes"
        const val COL_ID = "_id"
        const val COL_TITLE = "title"
        const val COL_CONTENT = "content"
        const val COL_CATEGORY = "category"
        const val COL_PINNED = "pinned"
        const val COL_CREATED = "created_at"
        const val COL_UPDATED = "updated_at"
    }
}
