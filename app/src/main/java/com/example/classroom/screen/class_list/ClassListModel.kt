package com.example.classroom.screen.student_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClassListModel(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(id) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = id)
        Text(text = name)
    }
}