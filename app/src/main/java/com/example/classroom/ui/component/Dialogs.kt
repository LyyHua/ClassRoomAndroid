import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.classroom.screen.class_list.ClassDetail
import com.example.classroom.screen.class_list.ClassMap
import com.example.classroom.screen.student_list.Student

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
                    val newClass = ClassDetail(id = classId, className = className)
                    onAddClass(newClass)
                    ClassMap[classId] = newClass // Update ClassMap
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
    onAddStudent: (Student) -> Unit,
    classId: String // Add classId parameter
) {
    var studentId by remember { mutableStateOf("") }
    var studentName by remember { mutableStateOf("") }
    var studentDob by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nhập thông tin sinh viên") },
        text = {
            Column {
                TextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("ID sinh viên") },
                    isError = errorMessage != null
                )
                TextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Tên sinh viên") }
                )
                TextField(
                    value = studentDob,
                    onValueChange = { studentDob = it },
                    label = { Text("Ngày tháng năm sinh") }
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
                    onAddStudent(Student(studentId = studentId, name = studentName, dob = studentDob, classId = classId)) // Pass classId
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