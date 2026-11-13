package com.example.asqizhong.data

object NoteQuery {

    fun matches(note: Note, query: String): Boolean {
        val q = query.trim()
        if (q.isEmpty()) return true
        return note.title.contains(q, ignoreCase = true) ||
            note.content.contains(q, ignoreCase = true)
    }

    fun apply(
        notes: List<Note>,
        query: String = "",
        category: String = NoteCategory.ALL,
        sortOrder: NoteSortOrder = NoteSortOrder.UPDATED_DESC
    ): List<Note> {
        val filtered = notes
            .filter { matches(it, query) }
            .filter { category == NoteCategory.ALL || it.category == category }

        return filtered.sortedWith(
            compareByDescending<Note> { it.pinned }.then(sortOrder.comparator())
        )
    }
}
