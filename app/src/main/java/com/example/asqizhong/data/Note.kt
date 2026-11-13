package com.example.asqizhong.data

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val pinned: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
