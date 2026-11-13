package com.example.asqizhong.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryNoteRepository(initial: List<Note> = emptyList()) : NoteRepository {

    private val _notes = MutableStateFlow(initial)
    override val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var nextId = (initial.maxOfOrNull { it.id } ?: 0L) + 1

    override fun getById(id: Long): Note? = _notes.value.firstOrNull { it.id == id }

    override fun insert(title: String, content: String, category: String): Long {
        val now = System.currentTimeMillis()
        val note = Note(
            id = nextId++,
            title = title,
            content = content,
            category = category,
            pinned = false,
            createdAt = now,
            updatedAt = now
        )
        _notes.value = _notes.value + note
        return note.id
    }

    override fun update(note: Note) {
        _notes.value = _notes.value.map { existing ->
            if (existing.id == note.id) {
                note.copy(updatedAt = System.currentTimeMillis())
            } else {
                existing
            }
        }
    }

    override fun delete(id: Long) {
        _notes.value = _notes.value.filterNot { it.id == id }
    }

    override fun setPinned(id: Long, pinned: Boolean) {
        _notes.value = _notes.value.map {
            if (it.id == id) it.copy(pinned = pinned) else it
        }
    }
}
