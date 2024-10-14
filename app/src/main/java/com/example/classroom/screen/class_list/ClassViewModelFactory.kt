package com.example.classroom.screen.class_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.classroom.sqlite.ClassViewModel
import com.example.classroom.sqlite.Repository

class ClassViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClassViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}