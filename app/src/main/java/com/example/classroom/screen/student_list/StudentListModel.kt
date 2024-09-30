package com.example.classroom.screen.student_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StudentListModel(
    modifier: Modifier = Modifier,
    id: Int,
    name: String,
    dob: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = id.toString())
        Text(text = name)
        Text(text = dob)
    }
}