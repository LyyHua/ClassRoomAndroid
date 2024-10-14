package com.example.classroom.screen.class_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.classroom.screen.student_list.ClassListModel

@Composable
fun ClassListScreen(
    modifier: Modifier = Modifier,
    onClassClicked: (String) -> Unit,
    isDeleteMode: Boolean = false,
    onClassDeleted: (String) -> Unit = {},
    classes: List<ClassDetail>
) {
    Column(modifier = modifier) {
        classes.forEach { classItem: ClassDetail ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (isDeleteMode) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Class",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                onClassDeleted(classItem.id)
                            }
                    )
                }
                ClassListModel(
                    id = classItem.id,
                    name = classItem.className,
                    onClick = { if (!isDeleteMode) onClassClicked(classItem.id) }
                )
            }
        }
    }
}