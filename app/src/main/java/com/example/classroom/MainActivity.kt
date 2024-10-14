package com.example.classroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.classroom.sqlite.Repository
import com.example.classroom.ui.theme.ClassRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClassRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
                    AppScreen(modifier =  Modifier.padding(innerpadding), repository = Repository(this))
                }
            }
        }
    }
}