package com.example.asqizhong.data

enum class NoteSortOrder(val label: String) {
    UPDATED_DESC("修改时间 · 新→旧"),
    UPDATED_ASC("修改时间 · 旧→新"),
    TITLE_ASC("标题 · A→Z"),
    TITLE_DESC("标题 · Z→A");

    fun comparator(): Comparator<Note> = when (this) {
        UPDATED_DESC -> compareByDescending { it.updatedAt }
        UPDATED_ASC -> compareBy { it.updatedAt }
        TITLE_ASC -> compareBy { it.title.lowercase() }
        TITLE_DESC -> compareByDescending { it.title.lowercase() }
    }
}
