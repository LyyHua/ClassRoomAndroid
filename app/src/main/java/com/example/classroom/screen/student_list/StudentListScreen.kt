package com.example.classroom.screen.student_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.classroom.R

@Composable
fun StudentListScreen(
    students: List<Student>,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {},
    isDeleteMode: Boolean = false,
    onStudentDeleted: (Student) -> Unit = {}
) {
    Box {
        Column {
            students.forEach { student ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    if (isDeleteMode) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Student",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    onStudentDeleted(student)
                                }
                        )
                    }
                    Text(
                        text = student.studentId,
                    )
                    Text(
                        text = student.name,
                    )
                    Text(
                        text = student.dob,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onBackButtonClicked
                ) {
                    Text(stringResource(R.string.back))
                }
            }
        }
    }
}