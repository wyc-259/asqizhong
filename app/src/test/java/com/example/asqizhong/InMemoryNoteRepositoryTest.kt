package com.example.asqizhong

import com.example.asqizhong.data.InMemoryNoteRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InMemoryNoteRepositoryTest {

    @Test
    fun insert_returnsIncrementingIds() {
        val repo = InMemoryNoteRepository()
        val id1 = repo.insert("标题一", "内容一", "工作")
        val id2 = repo.insert("标题二", "内容二", "学习")
        assertNotEquals(id1, id2)
        assertEquals(2, repo.notes.value.size)
    }

    @Test
    fun insert_noteIsRetrievable() {
        val repo = InMemoryNoteRepository()
        val id = repo.insert("标题", "内容", "生活")
        val note = repo.getById(id)
        assertNotNull(note)
        assertEquals("标题", note!!.title)
        assertEquals("内容", note.content)
        assertEquals("生活", note.category)
        assertFalse(note.pinned)
        assertEquals(note.createdAt, note.updatedAt)
        assertTrue(note.updatedAt > 0)
    }

    @Test
    fun update_changesTitleAndContent() {
        val repo = InMemoryNoteRepository()
        val id = repo.insert("旧标题", "旧内容", "工作")
        val original = repo.getById(id)!!
        repo.update(original.copy(title = "新标题", content = "新内容"))
        val updated = repo.getById(id)!!
        assertEquals("新标题", updated.title)
        assertEquals("新内容", updated.content)
        assertTrue(updated.updatedAt >= original.updatedAt)
    }

    @Test
    fun delete_removesNote() {
        val repo = InMemoryNoteRepository()
        val id = repo.insert("标题", "内容", "工作")
        repo.delete(id)
        assertNull(repo.getById(id))
        assertTrue(repo.notes.value.isEmpty())
    }

    @Test
    fun setPinned_togglesPin() {
        val repo = InMemoryNoteRepository()
        val id = repo.insert("标题", "内容", "工作")
        assertFalse(repo.getById(id)!!.pinned)
        repo.setPinned(id, true)
        assertTrue(repo.getById(id)!!.pinned)
        repo.setPinned(id, false)
        assertFalse(repo.getById(id)!!.pinned)
    }

    @Test
    fun getById_missing_returnsNull() {
        val repo = InMemoryNoteRepository()
        assertNull(repo.getById(999L))
    }
}
