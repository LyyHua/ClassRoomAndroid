import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun AddClassDialog(
    onDismiss: () -> Unit,
    onAddClass: (ClassDetail) -> Unit
) {
    var classId by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Class") },
        text = {
            Column {
                TextField(
                    value = classId,
                    onValueChange = { classId = it },
                    label = { Text("Class ID") },
                    isError = errorMessage != null
                )
                TextField(
                    value = className,
                    onValueChange = { className = it },
                    label = { Text("Class Name") }
                )
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (classId.isNotBlank() && className.isNotBlank()) {
                    onAddClass(ClassDetail(id = 0, classId = classId, className = className))
                    onDismiss()
                } else {
                    errorMessage = "Class ID and Name cannot be empty"
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddStudentDialog(
    onDismiss: () -> Unit,
    onAddStudent: (Student) -> Unit
) {
    var studentId by remember { mutableStateOf("") }
    var studentName by remember { mutableStateOf("") }
    var studentDob by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Student") },
        text = {
            Column {
                TextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("Student ID") },
                    isError = errorMessage != null
                )
                TextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name") }
                )
                TextField(
                    value = studentDob,
                    onValueChange = { studentDob = it },
                    label = { Text("Date of Birth") }
                )
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (studentId.isNotBlank() && studentName.isNotBlank() && studentDob.isNotBlank()) {
                    onAddStudent(Student(id = 0, name = studentName, studentId = studentId, dob = studentDob))
                    onDismiss()
                } else {
                    errorMessage = "All fields must be filled"
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}