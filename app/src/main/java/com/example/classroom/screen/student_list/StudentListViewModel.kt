package com.example.classroom.screen.student_list

data class Student(val studentId: String, val name: String, val dob: String, val classId: String)

var classStudentMap = mapOf(
    "MA006.O111" to listOf(
        Student(
            studentId = "23520906",
            name = "Hua Van Ly",
            dob = "18/08/2005",
            classId = "MA006.O111"
        ),
        Student(
            studentId = "23520917",
            name = "Ho Nguyen Minh",
            dob = "25/02/2005",
            classId = "MA006.O111"
        )
    ),
    "SS006.P19" to listOf(
        Student(
            studentId = "23520910",
            name = "Tran Quang Manh",
            dob = "13/05/2005",
            classId = "SS006.P19"
        ),
        Student(
            studentId = "23520912",
            name = "Phan Duc Minh",
            dob = "06/09/2005",
            classId = "SS006.P19"
        )
    ),
    "SS004.P14" to listOf(
        Student(
            studentId = "23520914",
            name = "Nguyen Van A",
            dob = "01/01/2005",
            classId = "SS004.P14"
        ),
        Student(
            studentId = "23520915",

            name = "Le Thi B",
            dob = "02/02/2005",
            classId = "SS004.P14"
        )
    )
)