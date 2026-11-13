package com.example.asqizhong.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SqliteNoteRepository(context: Context) : NoteRepository {

    private val dbHelper = NoteDatabaseHelper(context.applicationContext)

    private val _notes = MutableStateFlow(emptyList<Note>())
    override val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        reload()
    }

    private fun reload() {
        scope.launch { _notes.value = dbHelper.readAll() }
    }

    override fun getById(id: Long): Note? = dbHelper.readById(id)

    override fun insert(title: String, content: String, category: String): Long {
        val id = dbHelper.insert(title, content, category)
        reload()
        return id
    }

    override fun update(note: Note) {
        dbHelper.update(note)
        reload()
    }

    override fun delete(id: Long) {
        dbHelper.delete(id)
        reload()
    }

    override fun setPinned(id: Long, pinned: Boolean) {
        dbHelper.setPinned(id, pinned)
        reload()
    }
}
