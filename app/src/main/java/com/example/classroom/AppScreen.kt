package com.example.classroom

import AddClassDialog
import AddStudentDialog
import com.example.classroom.screen.class_list.ClassMap
import com.example.classroom.screen.student_list.Student
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.classroom.screen.class_list.ClassDetail
import com.example.classroom.screen.student_list.classStudentMap
import com.example.classroom.screen.class_list.ClassListScreen
import com.example.classroom.screen.class_list.ClassViewModelFactory
import com.example.classroom.screen.student_list.StudentListScreen
import com.example.classroom.sqlite.ClassViewModel
import com.example.classroom.sqlite.Repository

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    repository: Repository
) {
    val classViewModel: ClassViewModel = viewModel(factory = ClassViewModelFactory(repository))
    val classes by classViewModel.classes
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "Start"

    var students by remember { mutableStateOf(emptyList<Student>()) }
    var isDeleteMode by remember { mutableStateOf(false) }
    var showAddClassDialog by remember { mutableStateOf(false) }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var currentClassId by remember { mutableStateOf("MA006.O111") }

    Scaffold(
        topBar = {
            AppBar(
                currentRoute = currentRoute,
                currentClassId = currentClassId,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    isDeleteMode = false // Reset delete mode when navigating up
                    currentClassId = "MA006.O111" // Reset currentClassId before navigating up
                    navController.navigateUp()
                },
                onAddClicked = {
                    if (currentRoute == "Start") {
                        showAddClassDialog = true
                    } else {
                        showAddStudentDialog = true
                    }
                },
                onDeleteClicked = { isDeleteMode = !isDeleteMode }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Start",
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = "Start") {
                ClassListScreen(
                    onClassClicked = { classId ->
                        currentClassId = classId
                        students = repository.getStudentsByClassId(classId)
                        isDeleteMode = false // Reset delete mode when changing class
                        navController.navigate("Class/$classId")
                    },
                    isDeleteMode = isDeleteMode,
                    onClassDeleted = { classId ->
                        classViewModel.deleteClass(classId)
                    },
                    classes = classes,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            composable(route = "Class/{id}") { backStackEntry ->
                val classId = backStackEntry.arguments?.getString("id") ?: "Unknown"
                currentClassId = classId
                students = repository.getStudentsByClassId(classId) // Ensure students are updated
                StudentListScreen(
                    students = students,
                    onBackButtonClicked = {
                        isDeleteMode = false // Reset delete mode when navigating back
                        currentClassId = "MA006.O111" // Reset currentClassId before navigating back
                        navController.navigate("Start")
                    },
                    isDeleteMode = isDeleteMode,
                    onStudentDeleted = { student ->
                        repository.deleteStudent(student.studentId)
                        students = repository.getStudentsByClassId(classId)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }

    if (showAddClassDialog) {
        AddClassDialog(
            onDismiss = { showAddClassDialog = false },
            onAddClass = { newClass ->
                classViewModel.addClass(newClass)
                ClassMap[newClass.id] = newClass // Update ClassMap
            }
        )
    }

    if (showAddStudentDialog) {
        AddStudentDialog(
            onDismiss = { showAddStudentDialog = false },
            onAddStudent = { newStudent ->
                repository.insertStudent(newStudent)
                students = repository.getStudentsByClassId(currentClassId) // Update students list
            },
            classId = currentClassId // Pass currentClassId to AddStudentDialog
        )
    }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentRoute: String,
    currentClassId: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onAddClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val className = if (currentRoute == "Start") "Class" else ClassMap[currentClassId]?.className ?: "Unknown Class"

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(className)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_options)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.add)) },
                    onClick = {
                        expanded = false
                        onAddClicked()
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.delete)) },
                    onClick = {
                        expanded = false
                        onDeleteClicked()
                    }
                )
            }
        }
    )
}