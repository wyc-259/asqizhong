package com.example.asqizhong.data

import kotlinx.coroutines.flow.StateFlow

interface NoteRepository {
    val notes: StateFlow<List<Note>>

    fun getById(id: Long): Note?

    fun insert(title: String, content: String, category: String): Long

    fun update(note: Note)

    fun delete(id: Long)

    fun setPinned(id: Long, pinned: Boolean)
}
