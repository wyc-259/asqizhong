package com.example.asqizhong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.asqizhong.data.SqliteNoteRepository
import com.example.asqizhong.ui.NotePadApp

class MainActivity : ComponentActivity() {

    private val repository by lazy { SqliteNoteRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotePadApp(repository = repository)
        }
    }
}
