package com.example.classroom

import AddClassDialog
import AddStudentDialog
import ClassMap
import Student
import androidx.annotation.StringRes
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import classStudentMap
import com.example.classroom.screen.class_list.ClassListScreen
import com.example.classroom.screen.student_list.StudentListScreen

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "Start"

    var students by remember { mutableStateOf(emptyList<Student>()) }
    var classes by remember { mutableStateOf(ClassMap.values.toList()) }
    var isDeleteMode by remember { mutableStateOf(false) }
    var mutableClassStudentMap by remember { mutableStateOf(classStudentMap.toMutableMap()) }
    var mutableClassMap by remember { mutableStateOf(ClassMap.toMutableMap()) }
    var showAddClassDialog by remember { mutableStateOf(false) }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var currentClassId by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            AppBar(
                currentRoute = currentRoute,
                currentClassId = currentClassId,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    isDeleteMode = false // Reset delete mode when navigating up
                    currentClassId = 0 // Reset currentClassId before navigating up
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
                        students = mutableClassStudentMap[classId] ?: emptyList()
                        isDeleteMode = false // Reset delete mode when changing class
                        navController.navigate("Class/$classId")
                    },
                    isDeleteMode = isDeleteMode,
                    onClassDeleted = { classId ->
                        mutableClassStudentMap = mutableClassStudentMap.toMutableMap().apply {
                            remove(classId)
                        }
                        mutableClassMap = mutableClassMap.toMutableMap().apply {
                            remove(classId)
                        }
                        classStudentMap = mutableClassStudentMap // Ensure the global map is updated
                        ClassMap = mutableClassMap // Ensure the global map is updated
                        classes = mutableClassMap.values.toList() // Update the classes state
                    },
                    classes = classes,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            composable(route = "Class/{classId}") { backStackEntry ->
                val classId = backStackEntry.arguments?.getString("classId")?.toIntOrNull() ?: 0
                currentClassId = classId
                StudentListScreen(
                    students = mutableClassStudentMap[classId] ?: emptyList(),
                    onBackButtonClicked = {
                        isDeleteMode = false // Reset delete mode when navigating back
                        currentClassId = 0 // Reset currentClassId before navigating back
                        navController.navigate("Start")
                    },
                    isDeleteMode = isDeleteMode,
                    onStudentDeleted = { student ->
                        students = students.filter { it.id != student.id }
                        mutableClassStudentMap = mutableClassStudentMap.toMutableMap().apply {
                            put(classId, students)
                        }
                        classStudentMap = mutableClassStudentMap // Ensure the global map is updated
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
                val newId = (mutableClassMap.keys.maxOrNull() ?: 0) + 1
                mutableClassMap = mutableClassMap.toMutableMap().apply {
                    put(newId, newClass.copy(id = newId))
                }
                mutableClassStudentMap = mutableClassStudentMap.toMutableMap().apply {
                    put(newId, emptyList())
                }
                ClassMap = mutableClassMap // Ensure the global map is updated
                classStudentMap = mutableClassStudentMap // Ensure the global map is updated
                classes = mutableClassMap.values.toList() // Update the classes state
            }
        )
    }

    if (showAddStudentDialog) {
        AddStudentDialog(
            onDismiss = { showAddStudentDialog = false },
            onAddStudent = { newStudent ->
                val newId = (mutableClassStudentMap[currentClassId]?.maxOfOrNull { it.id } ?: 0) + 1
                val updatedStudents = (mutableClassStudentMap[currentClassId] ?: emptyList()) + newStudent.copy(id = newId)
                mutableClassStudentMap = mutableClassStudentMap.toMutableMap().apply {
                    put(currentClassId, updatedStudents)
                }
                classStudentMap = mutableClassStudentMap // Ensure the global map is updated
                students = updatedStudents // Update the students state
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentRoute: String,
    currentClassId: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onAddClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val className = if (currentRoute == "Start") "Class" else ClassMap[currentClassId]?.classId ?: "Unknown Class"

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