package com.example.asqizhong.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.asqizhong.data.NoteRepository
import com.example.asqizhong.ui.theme.AsqizhongTheme

sealed interface Screen {
    data object NoteList : Screen
    data class NoteEditor(val noteId: Long?) : Screen
}

@Composable
fun NotePadApp(repository: NoteRepository) {
    var darkTheme by remember { mutableStateOf(false) }
    var screen by remember { mutableStateOf<Screen>(Screen.NoteList) }

    AsqizhongTheme(darkTheme = darkTheme) {
        when (val current = screen) {
            Screen.NoteList -> NoteListScreen(
                repository = repository,
                darkTheme = darkTheme,
                onToggleTheme = { darkTheme = !darkTheme },
                onNewNote = { screen = Screen.NoteEditor(null) },
                onEditNote = { id -> screen = Screen.NoteEditor(id) }
            )

            is Screen.NoteEditor -> NoteEditorScreen(
                repository = repository,
                noteId = current.noteId,
                darkTheme = darkTheme,
                onToggleTheme = { darkTheme = !darkTheme },
                onBack = { screen = Screen.NoteList }
            )
        }
    }
}
