package com.example.asqizhong

import com.example.asqizhong.data.Note
import com.example.asqizhong.data.NoteQuery
import com.example.asqizhong.data.NoteSortOrder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteQueryTest {

    private fun note(
        id: Long,
        title: String,
        content: String,
        category: String,
        pinned: Boolean,
        updatedAt: Long
    ) = Note(
        id = id,
        title = title,
        content = content,
        category = category,
        pinned = pinned,
        createdAt = 0L,
        updatedAt = updatedAt
    )

    private val notes = listOf(
        note(1, "Alpha", "first meeting", "工作", pinned = true, updatedAt = 300L),
        note(2, "Charlie", "shopping list", "生活", pinned = false, updatedAt = 100L),
        note(3, "Bravo", "second study plan", "学习", pinned = false, updatedAt = 200L)
    )

    @Test
    fun emptyQuery_returnsAll() {
        assertEquals(3, NoteQuery.apply(notes, "").size)
    }

    @Test
    fun blankQuery_returnsAll() {
        assertEquals(3, NoteQuery.apply(notes, "   ").size)
    }

    @Test
    fun searchByTitle_isCaseInsensitive() {
        assertEquals(listOf(1L), NoteQuery.apply(notes, "alpha").map { it.id })
        assertEquals(listOf(2L), NoteQuery.apply(notes, "CHARLIE").map { it.id })
    }

    @Test
    fun searchByContent() {
        assertEquals(listOf(2L), NoteQuery.apply(notes, "shopping").map { it.id })
        assertEquals(listOf(1L), NoteQuery.apply(notes, "meeting").map { it.id })
    }

    @Test
    fun searchNoMatch_returnsEmpty() {
        assertTrue(NoteQuery.apply(notes, "不存在的关键词").isEmpty())
    }

    @Test
    fun filterByCategory() {
        assertEquals(listOf(3L), NoteQuery.apply(notes, "", "学习").map { it.id })
    }

    @Test
    fun pinnedNotesAlwaysFirst() {
        val result = NoteQuery.apply(notes)
        assertTrue(result.first().pinned)
    }

    @Test
    fun sortByUpdatedDesc_isDefault() {
        assertEquals(listOf(1L, 3L, 2L), NoteQuery.apply(notes).map { it.id })
    }

    @Test
    fun sortByUpdatedAsc() {
        assertEquals(
            listOf(1L, 2L, 3L),
            NoteQuery.apply(notes, sortOrder = NoteSortOrder.UPDATED_ASC).map { it.id }
        )
    }

    @Test
    fun sortWithoutPinned_byUpdated() {
        val unpinned = notes.map { it.copy(pinned = false) }
        assertEquals(
            listOf(1L, 3L, 2L),
            NoteQuery.apply(unpinned, sortOrder = NoteSortOrder.UPDATED_DESC).map { it.id }
        )
        assertEquals(
            listOf(2L, 3L, 1L),
            NoteQuery.apply(unpinned, sortOrder = NoteSortOrder.UPDATED_ASC).map { it.id }
        )
    }

    @Test
    fun sortWithoutPinned_byTitle() {
        val unpinned = notes.map { it.copy(pinned = false) }
        assertEquals(
            listOf(1L, 3L, 2L),
            NoteQuery.apply(unpinned, sortOrder = NoteSortOrder.TITLE_ASC).map { it.id }
        )
        assertEquals(
            listOf(2L, 3L, 1L),
            NoteQuery.apply(unpinned, sortOrder = NoteSortOrder.TITLE_DESC).map { it.id }
        )
    }
}
