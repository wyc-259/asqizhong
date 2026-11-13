package com.example.asqizhong.data

object NoteCategory {
    const val ALL = "全部"
    const val DEFAULT = "未分类"

    val PRESET: List<String> = listOf("工作", "学习", "生活", "旅行", "其他")

    fun allOptions(): List<String> = listOf(DEFAULT) + PRESET
}
