package com.example.classroom.sqlite

import com.example.classroom.screen.class_list.ClassDetail
import DatabaseHelper
import com.example.classroom.screen.student_list.Student
import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class Repository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertClass(classDetail: ClassDetail): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CLASS_ID, classDetail.id)
            put(DatabaseHelper.COLUMN_CLASS_NAME, classDetail.className)
        }
        return db.insert(DatabaseHelper.TABLE_CLASSES, null, values)
    }

    fun deleteClass(classId: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DatabaseHelper.TABLE_CLASSES, "${DatabaseHelper.COLUMN_CLASS_ID} = ?", arrayOf(classId))
    }

    fun getAllClasses(): List<ClassDetail> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DatabaseHelper.TABLE_CLASSES, null, null, null, null, null, null)
        val classes = mutableListOf<ClassDetail>()
        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_CLASS_ID))
                val className = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_CLASS_NAME))
                classes.add(ClassDetail(id, className))
            }
        }
        cursor.close()
        return classes
    }

    fun insertStudent(student: Student): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_STUDENT_ID, student.studentId)
            put(DatabaseHelper.COLUMN_STUDENT_NAME, student.name)
            put(DatabaseHelper.COLUMN_STUDENT_DOB, student.dob)
            put(DatabaseHelper.COLUMN_STUDENT_CLASS_ID, student.classId)
        }
        return db.insert(DatabaseHelper.TABLE_STUDENTS, null, values)
    }

    fun deleteStudent(studentId: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DatabaseHelper.TABLE_STUDENTS, "${DatabaseHelper.COLUMN_STUDENT_ID} = ?", arrayOf(studentId))
    }

    fun getStudentsByClassId(classId: String): List<Student> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DatabaseHelper.TABLE_STUDENTS, null, "${DatabaseHelper.COLUMN_STUDENT_CLASS_ID} = ?", arrayOf(classId), null, null, null)
        val students = mutableListOf<Student>()
        with(cursor) {
            while (moveToNext()) {
                val studentId = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_NAME))
                val dob = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_DOB))
                students.add(Student(studentId, name, dob, classId))
            }
        }
        cursor.close()
        return students
    }
}