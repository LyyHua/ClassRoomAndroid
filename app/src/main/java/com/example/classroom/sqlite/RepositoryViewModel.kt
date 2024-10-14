package com.example.classroom.sqlite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classroom.screen.class_list.ClassDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassViewModel(private val repository: Repository) : ViewModel() {
    val classes = mutableStateOf<List<ClassDetail>>(emptyList())

    init {
        loadClasses()
    }

    private fun loadClasses() {
        viewModelScope.launch {
            classes.value = withContext(Dispatchers.IO) {
                repository.getAllClasses()
            }
        }
    }

    fun addClass(classDetail: ClassDetail) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertClass(classDetail)
            }
            loadClasses()
        }
    }

    fun deleteClass(classId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteClass(classId)
            }
            loadClasses()
        }
    }
}